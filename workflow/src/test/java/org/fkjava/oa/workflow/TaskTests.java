package org.fkjava.oa.workflow;

import java.util.HashMap;
import java.util.Map;

import org.activiti.engine.IdentityService;
import org.fkjava.oa.commons.vo.Result;
import org.fkjava.oa.workflow.service.WorkflowService;
import org.fkjava.oa.workflow.vo.TaskForm;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = { WorkflowConfig.class })
@SpringBootTest()
public class TaskTests extends BasicProcessTests {

	@Autowired
	private IdentityService identityService;
	@Autowired
	private WorkflowService workflowService;

	@Before
	public void setUserId() {
		// 后面会通过拦截器设置
		// 获取当前用户的id，设置给流程引擎，让流程引擎知道当前用户是谁！
		this.identityService.setAuthenticatedUserId("lwq");
	}

	@Test
	public void findTask() {
		// 找出当前用户的待办任务
		int number = 0;// 页码
		Page<TaskForm> page = this.workflowService.findTasks(number, null, "createTime", "desc");

		Assert.assertNotNull("返回一页的待办任务，不能为空。但现在返回为null", page);
		Assert.assertTrue("必须要找到有待办任务，现在没有找到", page.getNumberOfElements() > 0);
	}

	@Test
	public void completeTask() {
		int number = 0;// 页码
		Page<TaskForm> page = this.workflowService.findTasks(number, null, "createTime", "desc");
		page.getContent().forEach(tf -> {
			Map<String, String[]> params = new HashMap<>();
			String taskId = tf.getTask().getId();

			Result result = this.workflowService.complete(taskId, params);

			Assert.assertNotNull(result);
			Assert.assertEquals(Result.STATUS_OK, result.getStatus());
		});
	}

	@Override
	public String getFileName() {
		return "/SimpleProcess.zip";
	}

	@Override
	public String getProcessDefinitionKey() {
		return "SimpleProcess";
	}

	@Override
	public Map<String, String[]> getParams() {
		return new HashMap<>();
	}
}
