package org.fkjava.oa.workflow;

import java.util.HashMap;
import java.util.List;
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
public class TaskTests2 extends BasicProcessTests {

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
	public void completeTask1() {
		this.test("第二个任务", "2");
	}

	@Test
	public void completeTask2() {
		this.test("第三个任务", "5");
	}

	private void test(String x, String action) {

		int number = 0;// 页码
		Page<TaskForm> page = this.workflowService.findTasks(number, null, "createTime", "desc");
		page.getContent().forEach(tf -> {
			Map<String, String[]> params = new HashMap<>();
			// key要跟配置在Form标签里面的id相同
			// 因为【走第二】的线上面有个条件表达式：${approveAction eq '2' }
			params.put("approveAction", new String[] { action });// 【2】希望【走第二】

			String taskId = tf.getTask().getId();

			Result result = this.workflowService.complete(taskId, params);

			Assert.assertNotNull(result);
			Assert.assertEquals(Result.STATUS_OK, result.getStatus());
		});

		page = this.workflowService.findTasks(number, null, "createTime", "desc");
		List<TaskForm> list = page.getContent();
		Assert.assertNotNull(list);
		// 预期【走第二】以后，新的任务名称应该是【第二个任务】
		Assert.assertEquals(x, list.get(0).getTask().getName());
	}

	@Override
	public String getFileName() {
		return "/SimpleProcess2.zip";
	}

	@Override
	public String getProcessDefinitionKey() {
		return "SimpleProcess2";
	}

	@Override
	public Map<String, String[]> getParams() {
		return new HashMap<>();
	}
}
