package org.fkjava.oa.workflow.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipInputStream;

import org.activiti.engine.FormService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.form.StartFormData;
import org.activiti.engine.form.TaskFormData;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.impl.identity.Authentication;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.fkjava.oa.commons.vo.Result;
import org.fkjava.oa.workflow.service.WorkflowService;
import org.fkjava.oa.workflow.vo.ProcessForm;
import org.fkjava.oa.workflow.vo.ProcessImage;
import org.fkjava.oa.workflow.vo.TaskForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class WorkflowServiceImpl implements WorkflowService {

	private static final Logger LOG = LoggerFactory.getLogger(WorkflowServiceImpl.class);
	@Autowired
	private RepositoryService repositoryService;
	@Autowired
	private FormService formService;
	@Autowired
	private RuntimeService runtimeService;
	@Autowired
	private TaskService taskService;
	@Autowired
	private HistoryService historyService;

	@Override
	public Result deploy(String name, InputStream in) {
		try (ZipInputStream zipInputStream = new ZipInputStream(in)) {
			// 部署流程对应的核心代码
			repositoryService.createDeployment()//
					.addZipInputStream(zipInputStream)// 添加压缩流
					.name(name)// 设置部署的文件名
					.deploy();// 执行部署

			return Result.of(Result.STATUS_OK);
		} catch (IOException e) {
			LOG.debug("部署流程定义出现问题：" + e.getMessage(), e);
			Result result = Result.of(Result.STATUS_ERROR, "部署流程定义出现问题：" + e.getMessage());
			return result;
		}
	}

	@Override
	public ProcessForm getStartForm(String key) {
		ProcessForm form = new ProcessForm();

		ProcessDefinition definition = this.repositoryService.createProcessDefinitionQuery()//
				.processDefinitionKey(key)// 根据流程定义的KEY查询数据
				.latestVersion()// 查询最后一个版本的数据
				.singleResult();// 返回唯一的记录
		form.setDefinition(definition);

		this.fillStartForm(form);

		return form;
	}

	public ProcessForm getStartFormById(String id) {
		ProcessForm form = new ProcessForm();

		ProcessDefinition definition = this.repositoryService.getProcessDefinition(id);
		form.setDefinition(definition);

		this.fillStartForm(form);

		return form;
	}

	private void fillStartForm(ProcessForm form) {
		ProcessDefinition definition = form.getDefinition();
		try {
			// 配置了启动事件的formKey才去查询表单内容
			if (definition.hasStartFormKey()) {
				// 表单文件对应的内容
				Object content = this.formService.getRenderedStartForm(definition.getId());
				form.setContent(content);
			}
		} catch (Exception e) {
			// 当流程定义没有【开始表单】的时候，就会出现异常
			// 没有【开始表单】，意味着不需要查询表单内容
			// 所以这里出现异常什么都不处理
		}
		// 表单文件的名称
		String formKey = this.formService.getStartFormKey(definition.getId());

		form.setFormKey(formKey);

		// 查询表单数据，里面会包含表单属性
		StartFormData data = this.formService.getStartFormData(definition.getId());
		form.setData(data);
	}

	@Override
	public Result startProcessInstance(String processDefinitionId, Map<String, String[]> params) {
		// 1.整理请求参数
		// 请求参数里面，参数的值是一个String[]，不方便使用
		// 整理的时候，如果发现参数的值长度为1表示只有一个参数，转换为String
		// 其他保持是String[]
		Map<String, Object> variables = new HashMap<>();
		params.forEach((key, values) -> {
			if (values.length == 1) {
				variables.put(key, values[0]);
			} else {
				variables.put(key, values);
			}
		});
		// 把备注信息获取出来，后面用于记录流程跟踪信息
		String remark = (String) variables.remove("remark");

		// 2.获取流程定义
		ProcessForm form = this.getStartFormById(processDefinitionId);

		// 3.保存业务数据
		String businessKey = this.saveBusinessData(form, params);

		// 4.启动流程实例
		// 第一个参数：流程定义的ID
		// 第二个参数：业务数据的主键值，用于关联流程实例和业务数据
		// 第三个参数：流程变量，其实就是请求参数，用于流程的下一步判断的走向
		ProcessInstance instance = this.runtimeService.startProcessInstanceById(processDefinitionId, businessKey,
				variables);

		// 5.记录流程跟踪信息
		this.log(form.getDefinition(), instance, remark);

		Result result = Result.of(Result.STATUS_OK);
		return result;
	}

	@Override
	public Page<TaskForm> findTasks(int number) {
		// 1.获取当前用户的ID
		String userId = Authentication.getAuthenticatedUserId();
		// 2.构建分页条件
		Pageable pageable = PageRequest.of(number, 10);
		// 3.构建查询对象
		TaskQuery query = this.taskService.createTaskQuery();
		// 3.1.根据任务的处理人查找数据
		query.taskAssignee(userId);
		// 3.2.根据任务的时间降序（倒序）
		query.orderByTaskCreateTime().desc();

		// 4.查询总记录数
		long count = query.count();

		// 5.查询当前页的数据
		List<Task> taskList = query.listPage((int) pageable.getOffset(), pageable.getPageSize());

		// 6.循环taskList，查询关联数据，构建TaskForm对象
		List<TaskForm> result = new LinkedList<>();
		taskList.forEach(task -> {
			TaskForm tf = new TaskForm();
			tf.setTask(task);

			this.fillTaskForm(tf);
			result.add(tf);
		});

		// 构建返回Page对象
		Page<TaskForm> page = new PageImpl<>(result, pageable, count);
		return page;
	}

	private void fillTaskForm(TaskForm tf) {
		Task task = tf.getTask();
		try {
			Object content = this.formService.getRenderedTaskForm(task.getId());
			tf.setContent(content);
		} catch (Exception e) {
		}
		TaskFormData data = this.formService.getTaskFormData(task.getId());
		ProcessDefinition definition = //
				this.repositoryService.getProcessDefinition(task.getProcessDefinitionId());
		String formKey = //
				this.formService.getTaskFormKey(definition.getId(), task.getTaskDefinitionKey());

		HistoricProcessInstance instance = this.historyService.createHistoricProcessInstanceQuery()//
				.processInstanceId(task.getProcessInstanceId())//
				.singleResult();

		tf.setData(data);
		tf.setDefinition(definition);
		tf.setFormKey(formKey);
		tf.setInstance(instance);
	}

	@Override
	public Result complete(String taskId, Map<String, String[]> params) {
		// 1.跟启动流程实例一样，要整理参数
		Map<String, Object> variables = new HashMap<>();
		params.forEach((key, values) -> {
			if (values.length == 1) {
				variables.put(key, values[0]);
			} else {
				variables.put(key, values);
			}
		});
		// 从Map里面删除一个键值对的时候，会返回key对应的value
		String remark = (String) variables.remove("remark");

		// 2.查询任务实例
		Task task = this.taskService.createTaskQuery().taskId(taskId).singleResult();

		// 3.查询流程定义
		ProcessDefinition definition = this.repositoryService//
				.getProcessDefinition(task.getProcessDefinitionId());

		// 4.查询流程实例
		ProcessInstance instance = this.runtimeService//
				.createProcessInstanceQuery()//
				.processInstanceId(task.getProcessInstanceId())//
				.singleResult();

		// 5.更新业务数据
		ProcessForm form = this.getStartFormById(definition.getId());
		this.saveBusinessData(form, params);

		// 6.完成任务
		this.taskService.complete(taskId, variables);

		// 7.记录流程跟踪信息
		this.log(definition, instance, task, remark);

		Result result = Result.of(Result.STATUS_OK);
		return result;
	}

	// 记录流程跟踪信息
	private void log(ProcessDefinition processDefinition, ProcessInstance instance, String remark) {
		// TODO 流程跟踪信息暂时未记录
	}

	// 保存业务数据到对应的数据库表
	private String saveBusinessData(ProcessForm form, Map<String, String[]> params) {
		// TODO 保存业务数据暂时不实现，需要考虑新增和修改的情况
		return null;
	}

	private void log(ProcessDefinition definition, ProcessInstance instance, Task task, String remark) {
		// TODO 记录任务的完成日志
	}

	@Override
	public Page<ProcessDefinition> findProcessDefinitions(Integer pageNumber, String keyword, String orderByProperty,
			String orderByDirection) {
		ProcessDefinitionQuery query = this.repositoryService.createProcessDefinitionQuery();
		if (keyword != null) {
			keyword = "%" + keyword + "%";
			query.processDefinitionNameLike(keyword);
		}
		// 排序属性和排序方向要按顺序设置
		// 设置排序的属性
		if ("key".equals(orderByProperty)) {
			query.orderByProcessDefinitionKey();
		} else if ("name".equals(orderByProperty)) {
			query.orderByProcessDefinitionName();
		} else if ("category".equals(orderByProperty)) {
			query.orderByProcessDefinitionCategory();
		}
		// 设置排序方向
		if ("asc".equals(orderByDirection)) {
			query.asc();
		} else {
			query.desc();
		}
		// 只查询最后一个版本的流程定义
		query.latestVersion();

		// 查询总记录数
		long count = query.count();

		// 查询流程定义列表
		Pageable pageable = PageRequest.of(pageNumber, 10);
		List<ProcessDefinition> content = query.listPage((int) pageable.getOffset(), pageable.getPageSize());

		Page<ProcessDefinition> page = new PageImpl<>(content, pageable, count);
		return page;
	}

	@Override
	public void suspendDefinition(String id) {
		this.repositoryService.suspendProcessDefinitionById(id);
	}

	@Override
	public void activeDefinition(String id) {
		this.repositoryService.activateProcessDefinitionById(id);
	}

	@Override
	public ProcessImage getDefinitionImage(String id) {
		ProcessDefinition definition = this.repositoryService.getProcessDefinition(id);
		String name = definition.getDiagramResourceName();// 图片名称
		try (InputStream in = this.repositoryService.getProcessDiagram(id);) {

			ProcessImage image = new ProcessImage(name, in);
			return image;
		} catch (IOException e) {
			LOG.error("无法获取流程图：" + e.getMessage(), e);
		}
		return null;
	}
}
