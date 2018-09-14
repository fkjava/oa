package org.fkjava.oa.identity.service;

import java.util.List;

import org.fkjava.oa.identity.domain.Role;
import org.fkjava.oa.identity.domain.User;

public interface IdentityService {

	void save(Role role);

	List<Role> findAllRoles();

	void deleteRole(String id);

	void save(User user);

}
