package org.fkjava.oa.hrm.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.fkjava.oa.commons.domain.UUIDEntity;

@Entity
@Table(name = "hrm_leave_type")
public class LeaveType extends UUIDEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
