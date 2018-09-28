package org.fkjava.oa.hrm.service;

import java.util.List;

import org.fkjava.oa.commons.vo.Result;
import org.fkjava.oa.hrm.domain.Department;

public interface DepartmentService {

	void save(Department dept);

	List<Department> findTopDepartments();

	Result deleteById(String id);

	/**
	 * 
	 * @param moveType           前面、里面、后面
	 * @param departmentId       移动的节点
	 * @param targetDepartmentId 目标节点
	 * @return
	 */
	Result move(String moveType, String departmentId, String targetDepartmentId);

}
