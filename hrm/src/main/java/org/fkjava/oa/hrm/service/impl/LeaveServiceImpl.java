package org.fkjava.oa.hrm.service.impl;

import java.util.List;

import org.fkjava.oa.commons.vo.Result;
import org.fkjava.oa.hrm.dao.LeaveTypeRepository;
import org.fkjava.oa.hrm.domain.LeaveType;
import org.fkjava.oa.hrm.service.LeaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class LeaveServiceImpl implements LeaveService {

	@Autowired
	private LeaveTypeRepository typeRepository;

	@Override
	public List<LeaveType> findAllTypes() {
		return typeRepository.findAll();
	}

	@Override
	public void save(LeaveType type) {
		if (StringUtils.isEmpty(type.getId())) {
			type.setId(null);
		}

		LeaveType old = this.typeRepository.findByName(type.getName());
		if (old == null) {
			this.typeRepository.save(type);
		} else if (old.getId().equals(type.getId())) {
			this.typeRepository.save(type);
		} else {
			throw new IllegalArgumentException("请假类型的名称不能重复");
		}
	}

	@Override
	public Result deleteType(String id) {
		this.typeRepository.deleteById(id);
		return Result.of(Result.STATUS_OK, "请假类型删除成功");
	}
}
