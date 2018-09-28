package org.fkjava.oa.hrm.controller;

import java.util.List;

import org.fkjava.oa.hrm.domain.Position;
import org.fkjava.oa.hrm.service.PositionService;
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
@RequestMapping("/hrm/position")
public class PositionController {

	@Autowired
	private PositionService positionService;

	@GetMapping
	public ModelAndView index() {
		ModelAndView mav = new ModelAndView("hrm/position/index");
		List<Position> positions = this.positionService.findAll();
		mav.addObject("positions", positions);
		return mav;
	}

	@PostMapping
	public String save(Position position) {
		this.positionService.save(position);
		return "redirect:/hrm/position";
	}

	@DeleteMapping("{id}")
	@ResponseBody
	public String delete(@PathVariable("id") String id) {
		this.positionService.delete(id);
		return "OK";
	}
}
