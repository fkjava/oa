package org.fkjava.oa.workflow;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Map;

import org.fkjava.oa.workflow.service.WorkflowService;
import org.fkjava.oa.workflow.vo.ProcessForm;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

/**
 * 基础的流程测试公共代码
 * 
 * @author lwq
 *
 */
public abstract class BasicProcessTests extends AbstractTransactionalJUnit4SpringContextTests {

	@Autowired
	private WorkflowService workflowService;

	public abstract String getFileName();

	public abstract String getProcessDefinitionKey();

	public abstract Map<String, String[]> getParams();

	@Before
	public void initData() throws URISyntaxException {
		// 使用URL的方式，我们可以得到文件名
		URL url = this.getClass().getResource(this.getFileName());
		URI uri = url.toURI();
		File file = new File(uri);
		workflowService.deploy(file);

		//
		ProcessForm form = this.workflowService.getStartForm(getProcessDefinitionKey());
		String processDefinitionId = form.getDefinition().getId();
		this.workflowService.startProcessInstance(processDefinitionId, getParams());
	}
}
