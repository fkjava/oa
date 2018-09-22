package org.fkjava.oa.note.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.fkjava.oa.commons.domain.UUIDEntity;
import org.fkjava.oa.identity.domain.User;

@Entity
@Table(name = "note")
public class Note extends UUIDEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String title;
	@ManyToOne()
	@JoinColumn(name = "type_id")
	private NoteType type;
	// 谁写的
	@ManyToOne
	@JoinColumn(name = "write_user_id")
	private User writeUser;
	// 什么时候写的
	@Temporal(TemporalType.TIMESTAMP)
	private Date writeTime;
	// 什么时候发布的
	@Temporal(TemporalType.TIMESTAMP)
	private Date publishTime;
	// 公告状态
	@Enumerated(EnumType.STRING)
	private NoteStatus status;
	// 公告内容
	@Column(length = 1024 * 1024)
	private String content;

	// 内部枚举
	public static enum NoteStatus {
		/**
		 * 草稿，默认的
		 */
		DRAFT,
		/**
		 * 已经发布
		 */
		PUBLISHED,
		/**
		 * 已经撤回的
		 */
		REVOKE;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public NoteType getType() {
		return type;
	}

	public void setType(NoteType type) {
		this.type = type;
	}

	public User getWriteUser() {
		return writeUser;
	}

	public void setWriteUser(User writeUser) {
		this.writeUser = writeUser;
	}

	public Date getWriteTime() {
		return writeTime;
	}

	public void setWriteTime(Date writeTime) {
		this.writeTime = writeTime;
	}

	public Date getPublishTime() {
		return publishTime;
	}

	public void setPublishTime(Date publishTime) {
		this.publishTime = publishTime;
	}

	public NoteStatus getStatus() {
		return status;
	}

	public void setStatus(NoteStatus status) {
		this.status = status;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
