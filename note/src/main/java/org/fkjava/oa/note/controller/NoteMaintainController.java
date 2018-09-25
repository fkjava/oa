package org.fkjava.oa.note.controller;

import java.util.List;

import org.fkjava.oa.commons.vo.Result;
import org.fkjava.oa.note.domain.Note;
import org.fkjava.oa.note.domain.NoteType;
import org.fkjava.oa.note.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/note/maintain")
public class NoteMaintainController {

	@Autowired
	private NoteService noteService;

	@GetMapping()
	public ModelAndView index(//
			@RequestParam(name = "pageNumber", defaultValue = "0") Integer pageNumber, //
			// 所有页面，都可以统一使用keyword来负责查询
			@RequestParam(name = "keyword", required = false) String keyword//
	) {
		ModelAndView mav = new ModelAndView("note/maintain/index");

		Page<Note> page = this.noteService.findNotes(keyword, pageNumber);
		mav.addObject("page", page);

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

	@GetMapping("publish/{id}")
	public String publish(@PathVariable("id") String id) {
		this.noteService.publish(id);
		return "redirect:/note/maintain";
	}

	@GetMapping("revoke")
	public String revoke(@RequestParam("id") String id, @RequestParam("revokeRemark") String revokeRemark) {
		this.noteService.revoke(id, revokeRemark);
		return "redirect:/note/maintain";
	}
}
