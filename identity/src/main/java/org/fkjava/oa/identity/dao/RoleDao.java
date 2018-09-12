package org.fkjava.oa.identity.dao;

import java.util.Optional;

import org.fkjava.oa.identity.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleDao extends JpaRepository<Role, String> {

	Optional<Role> findByRoleKey(String key);

}
