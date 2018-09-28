package org.fkjava.oa.hrm.dao;

import org.fkjava.oa.hrm.domain.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PositionDao extends JpaRepository<Position, String> {

	Position findByName(String name);

}
