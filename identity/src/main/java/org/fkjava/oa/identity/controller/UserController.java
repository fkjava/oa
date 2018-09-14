package org.fkjava.oa.identity.controller;

import java.util.Date;
import java.util.List;

import org.fkjava.oa.commons.DatePropertyEditor;
import org.fkjava.oa.identity.domain.Role;
import org.fkjava.oa.identity.domain.User;
import org.fkjava.oa.identity.service.IdentityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/identity/user")
public class UserController {

	@Autowired
	private IdentityService identityService;

	@GetMapping
	public ModelAndView index() {
		ModelAndView mav = new ModelAndView("identity/user/index");
		return mav;
	}

	@GetMapping("add")
	public ModelAndView add() {
		ModelAndView mav = new ModelAndView("identity/user/add");

		// 所有角色查询出来
		List<Role> roles = this.identityService.findAllRoles();
		mav.addObject("roles", roles);

		return mav;
	}

	@InitBinder
	public void bindDate(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, new DatePropertyEditor("yyyy-MM-dd"));
	}

	@PostMapping
	public String save(User user) {
		this.identityService.save(user);

		return "redirect:/identity/user";
	}
}
