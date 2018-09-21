package org.fkjava.oa.menu.domain;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.fkjava.oa.commons.domain.UUIDEntity;
import org.fkjava.oa.identity.domain.Role;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "sys_menu")
public class Menu extends UUIDEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private String url;
	private String method;
	// 对应的父节点（上级菜单）
	@ManyToOne
	@JoinColumn(name = "parent_id")
	@JsonIgnore // 转换为JSON的时候忽略掉
	private Menu parent;
	// 关系由parent来维护
	@OneToMany(mappedBy = "parent")
	@JsonProperty("children") // 生成的JSON取一个别名
	@OrderBy("name") // 查询集合的时候，加上order by关键字
	private List<Menu> child;
	// 角色和菜单的关系是多对多
	@ManyToMany(fetch = FetchType.EAGER)
	// joinColumns : 通过Menu找Role的时候使用
	// inverseJoinColumns : 在双向关联的时候，通过Role找Menu
	@JoinTable(name = "sys_menu_roles", //
			joinColumns = { @JoinColumn(name = "menu_id") }, //
			inverseJoinColumns = { @JoinColumn(name = "role_id") }//
	)
	private List<Role> roles;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public Menu getParent() {
		return parent;
	}

	public void setParent(Menu parent) {
		this.parent = parent;
	}

	public List<Menu> getChild() {
		return child;
	}

	public void setChild(List<Menu> child) {
		this.child = child;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
}
