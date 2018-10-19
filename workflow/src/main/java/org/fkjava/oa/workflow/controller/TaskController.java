package org.fkjava.oa.workflow.controller;

import org.fkjava.oa.workflow.service.WorkflowService;
import org.fkjava.oa.workflow.vo.TaskForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/workflow/task")
public class TaskController {

	@Autowired
	private WorkflowService workflowService;

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
		
		return mav;
	}
}
