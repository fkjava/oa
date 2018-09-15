package org.fkjava.oa.security.vo;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class UserDetails extends User {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// 为了能够显示姓名
	private String name;

	public UserDetails(org.fkjava.oa.identity.domain.User user, Collection<GrantedAuthority> authorities) {
		super(user.getLoginName(), user.getPassword(), authorities);
		// 扩展属性
		this.name = user.getName();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
