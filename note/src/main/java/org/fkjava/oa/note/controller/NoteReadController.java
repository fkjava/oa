package org.fkjava.oa.note.controller;

import org.fkjava.oa.note.domain.NoteRead;
import org.fkjava.oa.note.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/note/read")
public class NoteReadController {

	@Autowired
	private NoteService noteService;

	@GetMapping()
	public ModelAndView index(//
			@RequestParam(name = "pageNumber", defaultValue = "0") Integer pageNumber, //
			// 所有页面，都可以统一使用keyword来负责查询
			@RequestParam(name = "keyword", required = false) String keyword, //
			@RequestParam(value = "orderBy", defaultValue = "title") String orderByProperty, //
			@RequestParam(value = "orderByDirection", defaultValue = "asc") String orderByDirection//
	) {
		ModelAndView mav = new ModelAndView("note/read/index");

		// 查询当前用户可以读取的公告，并且把公告的状态也查询出来（还没有阅读的公告没有User对象）
		Page<NoteRead> page = this.noteService.findMyNotes(keyword, orderByProperty, orderByDirection, pageNumber);
		mav.addObject("page", page);

		return mav;
	}

	@GetMapping("{id}")
	public ModelAndView view(//
			@PathVariable("id") String id//
	) {
		ModelAndView mav = new ModelAndView("note/read/view");

		NoteRead read = this.noteService.findNoteReadByNoteId(id);
		mav.addObject("read", read);

		return mav;
	}

	@PostMapping("{id}")
	public String read(//
			@PathVariable("id") String id//
	) {
		this.noteService.read(id);
		return "redirect:/note/read";
	}
}
