package org.fkjava.oa.workflow.controller;

import java.util.Map;

import org.fkjava.oa.commons.vo.Result;
import org.fkjava.oa.workflow.service.WorkflowService;
import org.fkjava.oa.workflow.vo.TaskForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping("/workflow/task")
public class TaskController {

	private static final Logger LOG = LoggerFactory.getLogger(TaskController.class);
	@Autowired
	private WorkflowService workflowService;
	// 此对象在Spring Boot里面不需要自己创建
	// 如果不是使用Spring Boot，那么自己new一个
	@Autowired
	private ObjectMapper objectMapper;

	@GetMapping
	public ModelAndView index( //
			@RequestParam(name = "pageNumber", defaultValue = "0") Integer pageNumber, //
			// 所有页面，都可以统一使用keyword来负责查询
			@RequestParam(name = "keyword", required = false) String keyword, //
			@RequestParam(value = "orderBy", defaultValue = "createTime") String orderByProperty, //
			@RequestParam(value = "orderByDirection", defaultValue = "desc") String orderByDirection//
	) {
		ModelAndView mav = new ModelAndView("workflow/task/index");

		Page<TaskForm> page = this.workflowService.findTasks(pageNumber, keyword, orderByProperty, orderByDirection);
		mav.addObject("page", page);

		return mav;
	}

	@GetMapping("{id}")
	public ModelAndView detail(@PathVariable("id") String id) {
		ModelAndView mav = new ModelAndView("workflow/task/detail");

		TaskForm tf = this.workflowService.getTaskForm(id);
		mav.addObject("form", tf);

		// 把业务数据转换为JSON字符串
		String json = "{}";
		try {
			if (tf.getBusinessData() != null) {
				json = objectMapper.writeValueAsString(tf.getBusinessData());
			}
		} catch (JsonProcessingException e) {
			LOG.warn("无法把业务数据转换为JSON字符串，因为：{}，详细原因请启用DEBUG级别的日志记录来查看", e.getMessage());
			LOG.debug(e.getLocalizedMessage(), e);
		}
		mav.addObject("json", json);

		return mav;
	}

	@PostMapping("{id}")
	public ModelAndView complete(@PathVariable("id") String id, WebRequest request) {
		ModelAndView mav = new ModelAndView("redirect:/workflow/task");

		Map<String, String[]> params = request.getParameterMap();
		Result result = this.workflowService.complete(id, params);
		// 把结果放入Session
		request.setAttribute("result", result, WebRequest.SCOPE_SESSION);

		return mav;
	}
}
