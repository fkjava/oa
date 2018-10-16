package org.fkjava.oa.workflow.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipInputStream;

import org.activiti.engine.RepositoryService;
import org.fkjava.oa.commons.vo.Result;
import org.fkjava.oa.workflow.service.WorkflowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WorkflowServiceImpl implements WorkflowService {

	@Autowired
	private RepositoryService repositoryService;

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

}
