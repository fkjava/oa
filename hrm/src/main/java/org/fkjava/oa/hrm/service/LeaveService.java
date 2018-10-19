package org.fkjava.oa.hrm.service;

import java.util.List;

import org.fkjava.oa.commons.vo.Result;
import org.fkjava.oa.hrm.domain.LeaveType;

public interface LeaveService {

	List<LeaveType> findAllTypes();

	void save(LeaveType type);

	Result deleteType(String id);

}
