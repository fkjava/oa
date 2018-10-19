package org.fkjava.oa.hrm.controller;

import java.util.List;

import org.fkjava.oa.commons.vo.Result;
import org.fkjava.oa.hrm.domain.LeaveType;
import org.fkjava.oa.hrm.service.LeaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/hrm/leave/type")
public class LeaveTypeController {

	@Autowired
	private LeaveService leaveService;

	@GetMapping
	public ModelAndView index() {
		ModelAndView mav = new ModelAndView("hrm/leave/type/index");
		List<LeaveType> types = this.leaveService.findAllTypes();
		mav.addObject("type", types);
		return mav;
	}

	// 要求返回JSON的时候，调用此方法
	@GetMapping(produces = "application/json")
	@ResponseBody
	public List<LeaveType> findAllTypes() {
		return this.leaveService.findAllTypes();
	}

	@PostMapping
	public String save(LeaveType type) {
		this.leaveService.save(type);
		return "redirect:/hrm/leave/type";
	}

	@DeleteMapping("{id}")
	@ResponseBody
	public Result delete(@PathVariable("id") String id) {
		Result result = this.leaveService.deleteType(id);
		return result;
	}
}
