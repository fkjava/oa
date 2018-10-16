package org.fkjava.oa.workflow.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipInputStream;

import org.activiti.engine.FormService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.form.StartFormData;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.fkjava.oa.commons.vo.Result;
import org.fkjava.oa.workflow.service.WorkflowService;
import org.fkjava.oa.workflow.vo.ProcessForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WorkflowServiceImpl implements WorkflowService {

	@Autowired
	private RepositoryService repositoryService;
	@Autowired
	private FormService formService;
	@Autowired
	private RuntimeService runtimeService;

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
				// 表单文件的名称
				String formKey = this.formService.getStartFormKey(definition.getId());

				form.setFormKey(formKey);
				form.setContent(content);
			}
		} catch (Exception e) {
			// 当流程定义没有【开始表单】的时候，就会出现异常
			// 没有【开始表单】，意味着不需要查询表单内容
			// 所以这里出现异常什么都不处理
		}

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
		this.log(form.getDefinition(), instance);

		Result result = Result.of(Result.STATUS_OK);
		return result;
	}

	// 记录流程跟踪信息
	private void log(ProcessDefinition processDefinition, ProcessInstance instance) {
		// TODO 流程跟踪信息暂时未记录
	}

	// 保存业务数据到对应的数据库表
	private String saveBusinessData(ProcessForm form, Map<String, String[]> params) {
		// TODO 保存业务数据暂时不实现
		return null;
	}
}
