package org.fkjava.oa.identity.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.fkjava.oa.commons.domain.UUIDEntity;

@Entity
@Table(name = "id_role")
public class Role extends UUIDEntity implements Comparable<Role> {

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

	// hashCode和equals方法，是把对象放入HashSet里面的时候要用到的。
	// 首先根据hashCode决定对象的存储位置。如果目标位置有对象，那么就会调用equals方法比较两个对象是否相等。
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((roleKey == null) ? 0 : roleKey.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Role other = (Role) obj;
		if (roleKey == null) {
			if (other.roleKey != null)
				return false;
		} else if (!roleKey.equals(other.roleKey))
			return false;
		return true;
	}

	// 使用TreeSet的时候，就会调用compareTo方法，比较大小的大小，返回负数表示当前对象比参数传入的对象小。
	@Override
	public int compareTo(Role o) {
		// 使用唯一键的值来比较Role对象是否相等
		// 如果相等返回0
		return this.roleKey.compareTo(o.getRoleKey());
	}
}
