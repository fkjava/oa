package org.fkjava.oa.hrm.service;

import java.util.List;

import org.fkjava.oa.hrm.domain.Position;

public interface PositionService {

	List<Position> findAll();

	void save(Position position);

	void delete(String id);

}
