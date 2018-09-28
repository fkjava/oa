package org.fkjava.oa.hrm.service.impl;

import org.fkjava.oa.hrm.dao.EmployeeRepository;
import org.fkjava.oa.hrm.domain.Employee;
import org.fkjava.oa.hrm.service.EmployeeService;
import org.fkjava.oa.identity.domain.User;
import org.fkjava.oa.identity.service.IdentityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepository;
	@Autowired
	private IdentityService identityService;

	@Override
	public Page<User> findEmployees(String keyword, String orderByProperty, String orderByDirection,
			Integer pageNumber) {
		return null;
	}

	@Override
	@Transactional
	public void save(Employee employee) {
		// 保存用于信息，用于实现用户和员工的关联
		User user = employee.getUser();
		user = this.identityService.save(user);
		employee.setUser(user);

		this.employeeRepository.save(employee);
	}

}
