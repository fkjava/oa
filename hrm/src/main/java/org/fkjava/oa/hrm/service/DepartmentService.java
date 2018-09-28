package org.fkjava.oa.hrm.service;

import java.util.List;

import org.fkjava.oa.commons.vo.Result;
import org.fkjava.oa.hrm.domain.Department;

public interface DepartmentService {

	void save(Department dept);

	List<Department> findTopDepartments();

	Result deleteById(String id);

}
