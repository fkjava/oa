package org.fkjava.oa.identity.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.fkjava.oa.commons.domain.UUIDEntity;

@Entity
@Table(name = "id_role")
public class Role extends UUIDEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 角色名称
	 */
	private String name;
	/**
	 * 角色的唯一键
	 */
	@Column(unique = true)
	private String roleKey;
	/**
	 * 是否固定的，如果为true则表示固定的
	 */
	private boolean fixed;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRoleKey() {
		return roleKey;
	}

	public void setRoleKey(String roleKey) {
		this.roleKey = roleKey;
	}

	public boolean isFixed() {
		return fixed;
	}

	public void setFixed(boolean fixed) {
		this.fixed = fixed;
	}
}
