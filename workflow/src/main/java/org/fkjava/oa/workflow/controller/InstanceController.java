package org.fkjava.oa.workflow.controller;

import java.util.Map;

import org.fkjava.oa.workflow.service.WorkflowService;
import org.fkjava.oa.workflow.vo.ProcessForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/workflow/instance")
public class InstanceController {

	@Autowired
	private WorkflowService workflowService;

	// 显示启动流程实例的界面，比如要填写请假申请
	@GetMapping("{key}")
	public ModelAndView index(@PathVariable("key") String key) {
		ModelAndView mav = new ModelAndView();
		// 所有的流程，主体界面都是相同的。不同的业务私有的部分，通过FormService来解决。
		mav.setViewName("workflow/instance/index");
		ProcessForm form = workflowService.getStartForm(key);
		mav.addObject("form", form);

		return mav;
	}

	@PostMapping("{id}")
	public ModelAndView start(@PathVariable("id") String id, WebRequest request) {
		// 启动流程实例以后，重定向到【我的申请】
		ModelAndView mav = new ModelAndView("redirect:/workflow/history/instance");

		Map<String, String[]> params = request.getParameterMap();
		this.workflowService.startProcessInstance(id, params);

		return mav;
	}
}
