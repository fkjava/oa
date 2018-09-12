package org.fkjava.oa.identity.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/identity/user")
public class UserController {

	@GetMapping
	public ModelAndView index() {
		ModelAndView mav = new ModelAndView("identity/user/index");
		return mav;
	}
}
