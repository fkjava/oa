package org.fkjava.oa.hrm.dao;

import org.fkjava.oa.hrm.domain.LeaveType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LeaveTypeRepository extends JpaRepository<LeaveType, String> {

	LeaveType findByName(String name);

}
