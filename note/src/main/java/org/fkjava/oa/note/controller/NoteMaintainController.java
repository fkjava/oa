package org.fkjava.oa.note.controller;

import java.util.List;

import org.fkjava.oa.commons.vo.Result;
import org.fkjava.oa.note.domain.Note;
import org.fkjava.oa.note.domain.NoteType;
import org.fkjava.oa.note.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/note/maintain")
public class NoteMaintainController {

	@Autowired
	private NoteService noteService;

	@GetMapping()
	public ModelAndView index() {
		ModelAndView mav = new ModelAndView("note/maintain/index");

		return mav;
	}

	@GetMapping("add")
	public ModelAndView add() {
		ModelAndView mav = new ModelAndView("note/maintain/add");

		List<NoteType> types = noteService.findAllTypes();
		mav.addObject("types", types);

		return mav;
	}

	// 使用JSON提交到服务器的时候，接收参数就是整个请求体
	// @RequestBody注解表示把整个请求体都作为消息对象的全部内容
	@PostMapping
	// 使用@ResponseBody注解把整个对象作为响应体
	@ResponseBody
	public Result save(@RequestBody Note note) {

		Result result = this.noteService.save(note);

		return result;
	}
}
