package org.fkjava.oa.identity.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/identity/role")
public class RoleController {

	@GetMapping
	public ModelAndView index() {
		ModelAndView mav = new ModelAndView("identity/role/index");
		return mav;
	}
}
