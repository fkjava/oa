package org.fkjava.oa.identity.dao;

import java.util.List;
import java.util.Optional;

import org.fkjava.oa.identity.domain.User;
import org.fkjava.oa.identity.domain.User.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends JpaRepository<User, String> {

	User findByLoginName(String loginName);

	Optional<User> findByLoginNameAndStatusNot(String loginName, UserStatus status);

	List<User> findByStatus(UserStatus status);

}
