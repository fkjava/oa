package org.fkjava.oa.identity.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.fkjava.oa.commons.domain.UUIDEntity;

@Entity
@Table(name = "id_user")
public class User extends UUIDEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String name;
	@Column(unique = true)
	private String loginName;
	private String password;
	// 日期的格式
	@Temporal(TemporalType.DATE)
	private Date birthday;
	@ManyToMany
	@JoinTable(name = "id_user_roles")
	private List<Role> roles;
	// 让数据库保存枚举的时候，使用索引保存还是字符串保存
	// 使用字符串保存的时候，扩展性比较好
	@Enumerated(EnumType.STRING)
	private UserStatus status;

	public User() {
	}

	public User(String id) {
		super.setId(id);
	}

	/**
	 * 用户状态
	 * 
	 * @author lwq
	 *
	 */
	public static enum UserStatus {
		/**
		 * 正常、默认的
		 */
		NORMAL,
		/**
		 * 离职的
		 */
		SEPARATION;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public UserStatus getStatus() {
		return status;
	}

	public void setStatus(UserStatus status) {
		this.status = status;
	}
}
