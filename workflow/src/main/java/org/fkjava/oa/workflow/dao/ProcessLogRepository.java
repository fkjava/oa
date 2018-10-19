package org.fkjava.oa.workflow.dao;

import java.util.List;

import org.fkjava.oa.workflow.domain.ProcessLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProcessLogRepository extends JpaRepository<ProcessLog, String> {

	// 根据流程实例ID查询所有的流程跟踪信息
	List<ProcessLog> findByProcessInstanceIdOrderByTimeAsc(String processInstanceId);

}
