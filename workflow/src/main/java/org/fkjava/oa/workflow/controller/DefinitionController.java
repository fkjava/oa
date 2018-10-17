package org.fkjava.oa.workflow.controller;

import java.io.IOException;
import java.io.InputStream;

import org.activiti.engine.repository.ProcessDefinition;
import org.fkjava.oa.workflow.service.WorkflowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/workflow/definition")
public class DefinitionController {

	@Autowired
	private WorkflowService workflowService;

	@GetMapping
	public ModelAndView index( //
			@RequestParam(name = "pageNumber", defaultValue = "0") Integer pageNumber, //
			// 所有页面，都可以统一使用keyword来负责查询
			@RequestParam(name = "keyword", required = false) String keyword, //
			@RequestParam(value = "orderBy", defaultValue = "name") String orderByProperty, //
			@RequestParam(value = "orderByDirection", defaultValue = "asc") String orderByDirection//
	) {
		ModelAndView mav = new ModelAndView("workflow/definition/index");

		Page<ProcessDefinition> page = this.workflowService//
				.findProcessDefinitions(pageNumber, keyword, orderByProperty, orderByDirection);
		mav.addObject("page", page);

		return mav;
	}

	@PostMapping
	public String upload(//
			@RequestParam("file") MultipartFile file
	//
	) throws IOException {
		String name = file.getOriginalFilename();
		try (InputStream in = file.getInputStream()) {
			this.workflowService.deploy(name, in);
		}
		return "redirect:/workflow/definition";
	}

	@GetMapping("suspend/{id}")
	public String suspend(@PathVariable("id") String id) {
		this.workflowService.suspendDefinition(id);
		return "redirect:/workflow/definition";
	}

	@GetMapping("active/{id}")
	public String active(@PathVariable("id") String id) {
		this.workflowService.activeDefinition(id);
		return "redirect:/workflow/definition";
	}
}
