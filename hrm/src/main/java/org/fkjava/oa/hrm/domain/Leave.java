package org.fkjava.oa.hrm.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.fkjava.oa.commons.domain.BusinessData;

// 业务数据的主类
@Entity
@Table(name = "hrm_leave")
public class Leave extends BusinessData {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// 请假的明细
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "leave_id")
	private List<LeaveDetail> details;

	public List<LeaveDetail> getDetails() {
		return details;
	}

	public void setDetails(List<LeaveDetail> details) {
		this.details = details;
	}
}
