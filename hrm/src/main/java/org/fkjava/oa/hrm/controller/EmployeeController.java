package org.fkjava.oa.hrm.controller;

import java.util.Date;
import java.util.List;

import org.fkjava.oa.commons.DatePropertyEditor;
import org.fkjava.oa.hrm.domain.Department;
import org.fkjava.oa.hrm.domain.Employee;
import org.fkjava.oa.hrm.domain.Position;
import org.fkjava.oa.hrm.service.DepartmentService;
import org.fkjava.oa.hrm.service.EmployeeService;
import org.fkjava.oa.hrm.service.PositionService;
import org.fkjava.oa.identity.domain.Role;
import org.fkjava.oa.identity.domain.User;
import org.fkjava.oa.identity.service.IdentityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping("/hrm/employee")
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;
	@Autowired
	private PositionService positionService;
	@Autowired
	private DepartmentService departmentService;
	@Autowired
	private ObjectMapper objectMapper;
	@Autowired
	private IdentityService identityService;

	private Logger log = LoggerFactory.getLogger(DepartmentAction.class);

	@GetMapping
	public ModelAndView index(//
			@RequestParam(name = "pageNumber", defaultValue = "0") Integer pageNumber, //
			@RequestParam(name = "keyword", required = false) String keyword, //
			@RequestParam(value = "orderBy", defaultValue = "name") String orderByProperty, //
			@RequestParam(value = "orderByDirection", defaultValue = "asc") String orderByDirection//
	//
	) {
		ModelAndView mav = new ModelAndView("hrm/employee/index");

		Page<User> page = this.employeeService.findEmployees(keyword, orderByProperty, orderByDirection, pageNumber);
		mav.addObject("page", page);

		return mav;
	}

	@GetMapping("add")
	public ModelAndView add() {
		ModelAndView mav = new ModelAndView("hrm/employee/add");

		// 查询岗位、部门
		List<Position> positions = this.positionService.findAll();
		List<Department> topDepartments = this.departmentService.findTopDepartments();

		mav.addObject("positions", positions);

		String json = "{}";
		try {
			json = objectMapper.writeValueAsString(topDepartments);
		} catch (Exception ex) {
			log.error("无法把部门转换为JSON:" + ex.getMessage(), ex);
		}
		mav.addObject("departments", json);

		List<Role> roles = this.identityService.findAllRoles();
		mav.addObject("roles", roles);

		return mav;
	}

	@InitBinder
	public void bindDate(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, new DatePropertyEditor("yyyy-MM-dd"));
	}

	@PostMapping
	public String save(Employee employee) {
		this.employeeService.save(employee);
		return "redirect:/hrm/employee";
	}
}
