package org.fkjava.oa.note.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.fkjava.oa.commons.vo.Result;
import org.fkjava.oa.identity.domain.User;
import org.fkjava.oa.note.dao.NoteDao;
import org.fkjava.oa.note.dao.NoteReadDao;
import org.fkjava.oa.note.dao.NoteTypeDao;
import org.fkjava.oa.note.domain.Note;
import org.fkjava.oa.note.domain.Note.NoteStatus;
import org.fkjava.oa.note.domain.NoteRead;
import org.fkjava.oa.note.domain.NoteType;
import org.fkjava.oa.note.service.NoteService;
import org.fkjava.oa.security.vo.UserDetails;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class NoteServiceImpl implements NoteService, InitializingBean {

	@Autowired
	private NoteTypeDao noteTypeDao;
	@Autowired
	private NoteDao noteDao;
	@Autowired
	private NoteReadDao noteReadDao;

	@Override
	public void afterPropertiesSet() throws Exception {
		NoteType type = new NoteType();
		type.setDeletable(false);
		type.setModifiable(false);
		type.setRevocable(false);
		type.setName("撤回公告");
		type.setNumber(9999999);

		NoteType old = this.noteTypeDao.findByName(type.getName());
		if (old != null) {
			type.setId(old.getId());
		}

		this.save(type);
	}

	@Override
	public void save(NoteType type) {
		if (StringUtils.isEmpty(type.getId())) {
			type.setId(null);
		}
		// 检查name是否已经存在，如果存在则抛出异常
		// 如果不存在，就直接保存
		NoteType old = this.noteTypeDao.findByName(type.getName());
		if (old == null) {
			// 名字不重复，修改和新增都使用这里
			this.noteTypeDao.save(type);
		} else if (old != null && old.getId().equals(type.getId())) {
			// 名字重复，id也相同，表示当前类型没有该名字，可以修改
			this.noteTypeDao.save(type);
		} else {
			throw new IllegalArgumentException("公告类型的名称不能重复");
		}
	}

	@Override
	public List<NoteType> findAllTypes() {
		Sort sort = Sort.by("number");
		return this.noteTypeDao.findAll(sort);
	}

	@Override
	public Result deleteType(String id) {
		// 增加Note以后，需要重构这里。角色管理的删除也是需要重构。
		this.noteTypeDao.deleteById(id);

		Result result = new Result();
		result.setMessage("删除成功");
		result.setStatus(Result.STATUS_OK);
		return result;
	}

	@Override
	public Result save(Note note) {
		// 获取当前的登录信息
		UserDetails ud = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = new User();
		user.setId(ud.getId());

		if (StringUtils.isEmpty(note.getId())) {
			note.setId(null);
		}

		note.setPublishTime(null);
		note.setWriteTime(new Date());
		note.setWriteUser(user);
		note.setStatus(NoteStatus.DRAFT);

		this.noteDao.save(note);

		return Result.of(Result.STATUS_OK);
	}

	@Override
	public Page<Note> findNotes(String keyword, Integer pageNumber) {
		Sort sort = Sort.by(Order.asc("status"), Order.desc("title"));
		Pageable pageable = PageRequest.of(pageNumber, 8, sort);
		if (StringUtils.isEmpty(keyword)) {
			keyword = null;
		} else {
			// 如果使用Like关键字，需要在这里自己加%，使用Containing不需要自己加
		}

		Page<Note> page;
		if (keyword != null) {
			// 根据关键字查询，这里暂时只是利用title查询
			// 以后这里会改成使用【搜索引擎】来查询，这需要学习搜索引擎！
//			page = this.noteDao.findByTitleContainingOrderByStatusAscTitleAsc(keyword, pageable);
			page = this.noteDao.findByTitleContaining(keyword, pageable);
		} else {
			// 没有关键字，查询所有数据
//			Sort sort = Sort.by(Order.asc("status"), Order.desc("title"));
//			pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);
			page = this.noteDao.findAll(pageable);
		}
		return page;
	}

	@Override
	public void publish(String id) {
		// getOne返回永远不为空，是延迟加载的，返回代理对象
		// findById不延迟加载，返回Optional
		Note note = this.noteDao.findById(id).orElse(null);
		if (note != null) {
			note.setStatus(NoteStatus.PUBLISHED);
			note.setPublishTime(new Date());

			this.noteDao.save(note);
		}
	}

	@Override
	public void revoke(String id, String revokeRemark) {
		Note note = this.noteDao.findById(id).orElse(null);
		if (note != null) {
			// 标记原有的公告已经撤回
			note.setStatus(NoteStatus.REVOKED);
			this.noteDao.save(note);

			// 发布一个撤回公告
			// 撤回公告的类型是特殊的、固定的，需要在NoteType管理的时候，无论如何都加上一个固定的【撤回公告】。
			// 撤回公告，不可编辑、不可删除、不可撤回。
			// 在afterPropertiesSet方法里面去处理。
			NoteType type = this.noteTypeDao.findByName("撤回公告");
			Note newNote = new Note();
			String content = "<p>由于" + revokeRemark + "原因，撤回原有【" + note.getTitle() + "】公告，原文：</p>";
			content = content + "<s>" + note.getContent() + "</s>";
			newNote.setContent(content);
			newNote.setPublishTime(new Date());
			newNote.setStatus(NoteStatus.PUBLISHED);
			newNote.setTitle("撤回【" + note.getTitle() + "】");
			newNote.setType(type);
			newNote.setWriteTime(new Date());

			UserDetails ud = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			User user = new User();
			user.setId(ud.getId());

			newNote.setWriteUser(user);

			this.noteDao.save(newNote);
		}
	}

	@Override
	public Page<NoteRead> findMyNotes(String keyword, String orderByProperty, String orderByDirection,
			Integer pageNumber) {
		UserDetails ud = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = new User();
		user.setId(ud.getId());

		Order order;
		if (orderByDirection.equals("asc")) {
			order = Order.asc(orderByProperty);
		} else {
			order = Order.desc(orderByProperty);
		}

		Sort sort = Sort.by(order);
		Pageable pageable = PageRequest.of(pageNumber, 8, sort);
//		Pageable pageable = PageRequest.of(pageNumber, 8);

		// 使用外链接把公告和公告的阅读记录查询出来
//		select nr.*, n.*, u.*
//			from note n
//		    left outer join note_read nr on n.id = nr.note_id
//		    and nr.reader_id='ddddd'
		Page<NoteRead> page = this.noteReadDao.findByReader(user, pageable);

		return page;
	}

	@Override
	public NoteRead findNoteReadByNoteId(String id) {
		Note note = this.noteDao.findById(id).orElse(null);
		if (note != null) {

			UserDetails ud = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			User user = new User();
			user.setId(ud.getId());
			// 查询阅读记录
			Optional<NoteRead> optional = this.noteReadDao.findByNoteAndReader(note, user);

			// 写代码的时候，以写if、else为耻，用多态为荣
			NoteRead read = optional.orElseGet(() -> {
				NoteRead r = new NoteRead();
				r.setNote(note);
				return r;
			});

//			if( read == null ) {
//				read = new NoteRead();
//				read.setNote(note);
//			}
			return read;
		}

		return null;
	}

	@Override
	public void read(String id) {
		Note note = this.noteDao.findById(id).orElse(null);
		if (note != null) {
			// 原理已经读过，那么现在不需要再读取！
			UserDetails ud = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			User user = new User();
			user.setId(ud.getId());

			NoteRead old = this.noteReadDao.findByNoteAndReader(note, user).orElse(null);
			if (old == null) {

				// 新增阅读记录
				NoteRead read = new NoteRead();
				read.setNote(note);
				read.setReader(user);
				read.setReadTime(new Date());

				this.noteReadDao.save(read);
			}
		}
	}
}
