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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/system/menu")
@SessionAttributes("my_menus")
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

	@GetMapping("tree")
	@ResponseBody
	public List<Menu> tree(//
			@SessionAttribute(value = "my_menus", required = false) List<Menu> sessionMenus, //
			Model model) {
		// 在Session里面如果没有菜单，那么就调用业务逻辑层查询
		//if (sessionMenus == null) {
			log.debug("Session里面没有菜单，到业务逻辑层查询");
			List<Menu> menus = this.menuService.findCurrentUserMenuTree();
			model.addAttribute("my_menus", menus);
			return menus;
		//} else {
		//	log.debug("Session里面有菜单，直接返回");
		//	return sessionMenus;
		//}
	}
}
