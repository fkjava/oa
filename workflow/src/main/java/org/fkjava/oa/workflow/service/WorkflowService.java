package org.fkjava.oa.workflow.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.fkjava.oa.commons.vo.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public interface WorkflowService {

	public Logger LOG = LoggerFactory.getLogger(WorkflowService.class);

	/**
	 * 部署流程定义。传入的流，是一个ZIP格式压缩的流，里面可以包含多个文件、多个流程定义。
	 * 
	 * @param name 部署的文件名
	 * @param in   ZIP格式压缩的文件流
	 * @return
	 */
	public Result deploy(String name, InputStream in);

	/**
	 * 文件最终也是一个输入流，它去调用{@link #deploy(File)}
	 * 
	 * @param file
	 * @return
	 */
	// default方法不能final，因为继承多个接口有相同方法的时候，一定要重写接口的方法
	public default Result deploy(File file) {
		try (FileInputStream in = new FileInputStream(file)) {
			return this.deploy(file.getName(), in);
		} catch (IOException e) {
			LOG.debug("部署流程定义出现异常: " + e.getMessage(), e);
			Result result = Result.of(Result.STATUS_ERROR, "部署流程定义失败：" + e.getMessage());
			return result;
		}
	}
}