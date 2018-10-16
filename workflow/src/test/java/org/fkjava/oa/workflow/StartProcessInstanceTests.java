package org.fkjava.oa.workflow;

import java.util.HashMap;
import java.util.Map;

import org.fkjava.oa.commons.vo.Result;
import org.fkjava.oa.workflow.service.WorkflowService;
import org.fkjava.oa.workflow.vo.ProcessForm;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = { WorkflowConfig.class })
@SpringBootTest()
public class StartProcessInstanceTests extends AbstractJUnit4SpringContextTests {

	@Autowired
	private WorkflowService workflowService;

	@Test
	public void testGetStartForm() {
		ProcessForm form = this.workflowService.getStartForm("SimpleProcess");
		// 返回不为空
		Assert.assertNotNull("预期返回的流程表单不为空，但现在返回null", form);

		// 流程定义也不为空
		Assert.assertNotNull("预期返回的流程定义不为空，但现在返回null", form.getDefinition());
		Assert.assertNotNull("预期返回的流程表单内容不为空，但现在返回null", form.getContent());
		Assert.assertNotNull("预期返回的流程表单数据不为空，但现在返回null", form.getData());
	}

	@Test
	public void testStartProcessInstance() {
		ProcessForm form = this.workflowService.getStartForm("SimpleProcess");
		// 流程定义的ID
		String processDefinitionId = form.getDefinition().getId();
		// 所有的请求参数，传入给流程实例保存起来，用于判断流程的流转
		// 在Servlet里面，可以使用request.getParameterMap()获取所有请求参数，现在没有Servlet，所以创建一个HashMao模拟
		Map<String, String[]> params = new HashMap<>();
		Result result = this.workflowService.startProcessInstance(processDefinitionId, params);

		Assert.assertNotNull("启动结果不能为空，现在返回null", result);
		Assert.assertEquals("预期启动返回成功", Result.STATUS_OK, result.getStatus());
	}
}
