package org.fkjava.oa.identity.service;

import java.util.List;
import java.util.Optional;

import org.fkjava.oa.identity.domain.Role;
import org.fkjava.oa.identity.domain.User;
import org.fkjava.oa.identity.vo.Result;
import org.springframework.data.domain.Page;

public interface IdentityService {

	void save(Role role);

	List<Role> findAllRoles();

	void deleteRole(String id);

	void save(User user);

	Result checkLoginName(String loginName, String id);

	Page<User> findUsers(Integer pageNumber);

	User findUserById(String id);

	Result separation(String id);

	Optional<User> findUserByLoginName(String loginName);

}
