package org.fkjava.oa.workflow.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.fkjava.oa.workflow.service.WorkflowService;
import org.fkjava.oa.workflow.vo.ProcessImage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/workflow/history/instance")
public class HistoryInstanceController {

	@Autowired
	private WorkflowService workflowService;

	@GetMapping("image/{id}")
	public ResponseEntity<byte[]> image(@PathVariable("id") String processInstanceId)
			throws UnsupportedEncodingException {
		// 获取流程图
		ProcessImage image = this.workflowService.getInstanceImage(processInstanceId);

		// ok() == 200的状态码
		BodyBuilder builder = ResponseEntity.ok();
		// 文件类型
		builder.contentType(MediaType.IMAGE_PNG);
		// 图片的大小
		builder.contentLength(image.getData().length);
		// 设置文件名
		String name = image.getName();
		name = URLEncoder.encode(name, "UTF-8");
		builder.header("Content-Disposition", "attachment; filename*=UTF-8''" + name);

		ResponseEntity<byte[]> entity = builder.body(image.getData());

		return entity;
	}
}
