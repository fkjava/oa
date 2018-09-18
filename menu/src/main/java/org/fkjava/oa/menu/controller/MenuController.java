package org.fkjava.oa.menu.controller;

import java.util.List;

import org.fkjava.oa.identity.domain.Role;
import org.fkjava.oa.identity.service.IdentityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/system/menu")
public class MenuController {
	
	@Autowired
	private IdentityService identityService;

	@GetMapping
	public ModelAndView index() {

		ModelAndView mav = new ModelAndView("system/menu/index");
		
		List<Role> roles = this.identityService.findAllRoles();
		mav.addObject("roles", roles);

		return mav;
	}
}
