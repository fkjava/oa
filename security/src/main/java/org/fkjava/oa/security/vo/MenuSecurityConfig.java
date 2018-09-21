package org.fkjava.oa.security.vo;

import org.springframework.security.access.SecurityConfig;

public class MenuSecurityConfig extends SecurityConfig {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// 请求的URL
	private String url;
	// 请求方法
	private String method;

	/**
	 * role参数就是ROLE_USER这种类型的字符串
	 * 
	 * @param url
	 * @param method
	 * @param role
	 */
	public MenuSecurityConfig(String url, String method, String role) {
		super(role);
		this.url = url;
		this.method = method;
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

	@Override
	public String toString() {
		return "MenuSecurityConfig [getUrl()=" + getUrl() + ", getMethod()=" + getMethod() + ", getAttribute()="
				+ getAttribute() + "]";
	}
}
