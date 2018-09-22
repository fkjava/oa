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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((method == null) ? 0 : method.hashCode());
		result = prime * result + ((url == null) ? 0 : url.hashCode());
		result = prime * result + ((super.getAttribute() == null) ? 0 : super.getAttribute().hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		MenuSecurityConfig other = (MenuSecurityConfig) obj;
		if (method == null) {
			if (other.method != null)
				return false;
		} else if (!method.equals(other.method))
			return false;

		if (url == null) {
			if (other.url != null)
				return false;
		} else if (!url.equals(other.url))
			return false;

		if (super.getAttribute() == null) {
			if (other.getAttribute() != null)
				return false;
		} else if (!super.getAttribute().equals(other.getAttribute()))
			return false;
		return true;
	}
}
