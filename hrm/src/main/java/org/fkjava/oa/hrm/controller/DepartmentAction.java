package org.fkjava.oa.hrm.controller;

import java.util.List;

import org.fkjava.oa.commons.vo.Result;
import org.fkjava.oa.hrm.domain.Department;
import org.fkjava.oa.hrm.service.DepartmentService;
import org.fkjava.oa.identity.domain.User;
import org.fkjava.oa.identity.service.IdentityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping("/hrm/department")
public class DepartmentAction {

	@Autowired
	private DepartmentService departmentService;
	@Autowired
	private IdentityService identityService;

	private ObjectMapper objectMapper = new ObjectMapper();
	private Logger log = LoggerFactory.getLogger(DepartmentAction.class);

	@GetMapping
	public ModelAndView index() {

		ModelAndView mav = new ModelAndView("hrm/department/index");

		// 读取所有的在职用户，用于选择部门的责任人
		List<User> allNormalUsers = this.identityService.findAllNormalUsers();
		mav.addObject("allNormalUsers", allNormalUsers);

		// 查询所有的一级部门，并且转换为JSON
		List<Department> topDepartments = this.departmentService.findTopDepartments();
		String json = "{}";
		try {
			json = objectMapper.writeValueAsString(topDepartments);
		} catch (Exception ex) {
			log.error("无法把部门转换为JSON:" + ex.getMessage(), ex);
		}
		mav.addObject("json", json);

		return mav;
	}

	@PostMapping
	public String save(Department dept) {

		this.departmentService.save(dept);

		return "redirect:/hrm/department";
	}

	@DeleteMapping("{id}")
	@ResponseBody
	public Result delete(@PathVariable("id") String id) {

		Result result = this.departmentService.deleteById(id);

		return result;
	}

	@PostMapping({ "/move/{moveType}/{departmentId}/{targetDepartmentId}", "/move/{moveType}/{departmentId}/" })
	@ResponseBody
	public Result move(//
			@PathVariable("moveType") String moveType, //
			@PathVariable("departmentId") String departmentId, //
			@PathVariable(value = "targetDepartmentId", required = false) String targetDepartmentId//
	) {
		Result result = this.departmentService.move(moveType, departmentId, targetDepartmentId);
		return result;
	}
}
