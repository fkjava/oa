package org.fkjava.oa.note.service.impl;

import java.util.List;

import org.fkjava.oa.commons.vo.Result;
import org.fkjava.oa.note.dao.NoteTypeDao;
import org.fkjava.oa.note.domain.NoteType;
import org.fkjava.oa.note.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class NoteServiceImpl implements NoteService {

	@Autowired
	private NoteTypeDao noteTypeDao;

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
}
