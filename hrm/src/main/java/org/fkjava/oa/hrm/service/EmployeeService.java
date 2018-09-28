package org.fkjava.oa.hrm.service;

import org.fkjava.oa.hrm.domain.Employee;
import org.fkjava.oa.identity.domain.User;
import org.springframework.data.domain.Page;

public interface EmployeeService {

	Page<User> findEmployees(String keyword, String orderByProperty, String orderByDirection, Integer pageNumber);

	void save(Employee employee);
}
