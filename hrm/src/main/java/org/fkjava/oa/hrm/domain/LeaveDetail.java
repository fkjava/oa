package org.fkjava.oa.hrm.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.fkjava.oa.commons.domain.UUIDEntity;

@Entity
@Table(name = "hrm_leave_detail")
public class LeaveDetail extends UUIDEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 什么时候开始请假
	 */
	@Temporal(TemporalType.TIMESTAMP)
	private Date startTime;
	/**
	 * 请假到什么时候截止
	 */
	@Temporal(TemporalType.TIMESTAMP)
	private Date endTime;
	/**
	 * 请假的类型
	 */
	@ManyToOne()
	@JoinColumn(name = "type_id")
	private LeaveType type;
	/**
	 * 请假的时长
	 */
	private Double leaveHours;

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public LeaveType getType() {
		return type;
	}

	public void setType(LeaveType type) {
		this.type = type;
	}

	public Double getLeaveHours() {
		return leaveHours;
	}

	public void setLeaveHours(Double leaveHours) {
		this.leaveHours = leaveHours;
	}
}
