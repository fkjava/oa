package org.fkjava.oa.identity.dao;

import org.fkjava.oa.identity.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends JpaRepository<User, String> {

	User findByLoginName(String loginName);

}
