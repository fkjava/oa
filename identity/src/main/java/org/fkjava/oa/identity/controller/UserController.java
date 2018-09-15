package org.fkjava.oa.identity.controller;

import java.util.Date;
import java.util.List;

import org.fkjava.oa.commons.DatePropertyEditor;
import org.fkjava.oa.identity.domain.Role;
import org.fkjava.oa.identity.domain.User;
import org.fkjava.oa.identity.service.IdentityService;
import org.fkjava.oa.identity.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/identity/user")
public class UserController {

	@Autowired
	private IdentityService identityService;

	@GetMapping
	public ModelAndView index(//
			@RequestParam(name = "pageNumber", defaultValue = "0") Integer pageNumber
	//
	) {
		ModelAndView mav = new ModelAndView("identity/user/index");

		// 查数据
		Page<User> page = this.identityService.findUsers(pageNumber);
		mav.addObject("page", page);

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

	@GetMapping({ "/checkLoginName/{loginName}/{id}", "/checkLoginName/{loginName}/" })
	// 在之前讲Spring MVC的时候，内容协商的相关知识点
	@ResponseBody // 直接把对象返回给客户端，会自动使用转换器把对象转换为JSON
	public Result checkLoginName(//
			@PathVariable("loginName") String loginName, //
			@PathVariable(value = "id", required = false) String id) {

		Result result = this.identityService.checkLoginName(loginName, id);
		return result;
	}

	@GetMapping("{id}")
	public ModelAndView view(@PathVariable("id") String id) {
		ModelAndView mav = new ModelAndView("identity/user/add");

		// 所有角色查询出来
		List<Role> roles = this.identityService.findAllRoles();
		mav.addObject("roles", roles);

		User user = this.identityService.findUserById(id);
		mav.addObject("user", user);

		return mav;
	}

	@PostMapping("/separation/{id}")
	@ResponseBody
	public Result separation(@PathVariable("id") String id) {

		Result result = this.identityService.separation(id);

		return result;
	}
}
