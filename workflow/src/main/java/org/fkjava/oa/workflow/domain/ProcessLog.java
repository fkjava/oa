package org.fkjava.oa.workflow.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.fkjava.oa.commons.domain.UUIDEntity;
import org.fkjava.oa.identity.domain.User;

@Entity
@Table(name = "wf_proc_log")
public class ProcessLog extends UUIDEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// 谁
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	// 哪个流程定义
	private String processDefinitionId;
	// 哪个流程实例
	private String processInstanceId;
	// 哪个任务
	private String taskId;
	private String taskName;
	// 什么时候
	@Temporal(TemporalType.TIMESTAMP)
	private Date time;
	// 什么事情
	private String action;
	// 备注
	@Column(length = 2048) // 使用富文本编辑器的时候，要更长一些
	private String remark;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getProcessDefinitionId() {
		return processDefinitionId;
	}

	public void setProcessDefinitionId(String processDefinitionId) {
		this.processDefinitionId = processDefinitionId;
	}

	public String getProcessInstanceId() {
		return processInstanceId;
	}

	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getTaskName() {
		if (taskName == null) {
			return "启动流程";
		}
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
}
