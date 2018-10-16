package org.fkjava.oa.workflow;

import java.io.File;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import org.fkjava.oa.commons.vo.Result;
import org.fkjava.oa.workflow.service.WorkflowService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = { WorkflowConfig.class })
@SpringBootTest()
public class ProcessEngineTests extends AbstractTransactionalJUnit4SpringContextTests {

	@Autowired
	private WorkflowService workflowService;

	// 测试流程定义部署
	@Test
	public void testInputStreamDeploy() {
		// 直接获取InputStream的时候，会自动先获取URL，然后再调用URL的openStream方法打开一个输入流
		InputStream in = this.getClass().getResourceAsStream("/SimpleProcess.zip");
		Result result = workflowService.deploy("SimpleProcess.zip", in);
		// 预期部署要成功
		Assert.assertEquals(Result.STATUS_OK, result.getStatus());
	}

	@Test
	public void testFileDeploy() throws URISyntaxException {
		// 使用URL的方式，我们可以得到文件名
		URL url = this.getClass().getResource("/SimpleProcess.zip");
		URI uri = url.toURI();
		File file = new File(uri);
		Result result = workflowService.deploy(file);
		// 预期部署要成功
		Assert.assertEquals(Result.STATUS_OK, result.getStatus());
	}
}
