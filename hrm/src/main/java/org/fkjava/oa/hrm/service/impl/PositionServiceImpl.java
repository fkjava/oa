package org.fkjava.oa.hrm.service.impl;

import java.util.List;

import org.fkjava.oa.hrm.dao.PositionDao;
import org.fkjava.oa.hrm.domain.Position;
import org.fkjava.oa.hrm.service.PositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class PositionServiceImpl implements PositionService {

	@Autowired
	private PositionDao positionDao;

	@Override
	public List<Position> findAll() {
		return this.positionDao.findAll();
	}

	@Override
	public void save(Position position) {
		if (StringUtils.isEmpty(position.getId())) {
			position.setId(null);
		}
		Position old = this.positionDao.findByName(position.getName());
		if ((old != null && old.getId().equals(position.getId())) || old == null) {
			this.positionDao.save(position);
		} else {
			throw new IllegalArgumentException("岗位的名称不能重复");
		}
	}

	@Override
	public void delete(String id) {
		this.positionDao.deleteById(id);
	}
}
