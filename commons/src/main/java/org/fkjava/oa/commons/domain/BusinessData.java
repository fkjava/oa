package org.fkjava.oa.commons.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@MappedSuperclass
public abstract class BusinessData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// 主键
	@Id
	// 此处没有主键生成器，需要在保存数据之前，自己设置主键生成器
	private String id;

	// 谁提交申请
//	@ManyToOne()
//	@JoinColumn(name = "user_id")
//	private User user;
	@Column(name = "user_id")
	private String userId;

	// 什么时候提交的申请
	@Temporal(TemporalType.TIMESTAMP)
	private Date submitTime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Date getSubmitTime() {
		return submitTime;
	}

	public void setSubmitTime(Date submitTime) {
		this.submitTime = submitTime;
	}
}
