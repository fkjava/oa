package org.fkjava.oa.menu.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fkjava.oa.identity.domain.Role;
import org.fkjava.oa.identity.service.IdentityService;
import org.fkjava.oa.menu.domain.Menu;
import org.fkjava.oa.menu.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping("/system/menu")
public class MenuController {

	@Autowired
	private IdentityService identityService;
	@Autowired
	private MenuService menuService;

	private static final Logger log = LogManager.getLogger(MenuController.class);

	@GetMapping
	public ModelAndView index() {

		ModelAndView mav = new ModelAndView("system/menu/index");

		List<Role> roles = this.identityService.findAllRoles();
		mav.addObject("roles", roles);

//		List<Menu> menus = this.menuService.findAllMenus();
//		ObjectMapper mapper = new ObjectMapper();
//
//		// 把对象转换为JSON字符串
//		String json = "{}";
//		try {
//			json = mapper.writeValueAsString(menus);
//		} catch (Throwable e) {
//			// e.printStackTrace();
//			log.warn("无法把对象转换为JSON: " + e.getLocalizedMessage(), e);
//		}
//		mav.addObject("json", json);

		return mav;
	}

	@GetMapping("data")
	@ResponseBody
	public List<Menu> data() {
		List<Menu> menus = this.menuService.findAllMenus();
		return menus;
	}

	@PostMapping
	public String save(Menu menu) {

		// 调用业务逻辑层
		this.menuService.save(menu);

		return "redirect:/system/menu";
	}
}
