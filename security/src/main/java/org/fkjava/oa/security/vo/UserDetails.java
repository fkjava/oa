package org.fkjava.oa.security.vo;

import java.util.Collection;
import java.util.List;

import org.fkjava.oa.identity.domain.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class UserDetails extends User {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// 为了让业务逻辑层能够得到用户的id
	private String id;
	// 为了能够显示姓名
	private String name;
	private List<Role> roles;

	public UserDetails(org.fkjava.oa.identity.domain.User user, Collection<GrantedAuthority> authorities) {
		super(user.getLoginName(), user.getPassword(), authorities);
		// 扩展属性
		this.id = user.getId();
		this.name = user.getName();
		this.roles = user.getRoles();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
