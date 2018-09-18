package org.fkjava.oa.security.service.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;

public class MyFilterSecurityMetadataSource implements FilterInvocationSecurityMetadataSource, InitializingBean {

	// KEY = URL_METHOD
	// VALUE = 访问此URL具有的角色KEY
	private Map<String, Collection<ConfigAttribute>> uriConfigMap = new HashMap<>();

	@Override
	public void afterPropertiesSet() throws Exception {
		// 初始化元数据集合，此处的数据，应该从数据库读取出来生成的
		// 每个URL，加上请求方法可以作为判断依据
		// 访问此URL需要哪些角色？一个个加上！
		uriConfigMap.put("GET_/identity/user", SecurityConfig.createList("ROLE_ADMIN", "ROLE_SYS"));
	}

	@Override
	public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {

		// 注意：这个强制类型转换不会出现ClassCastException，因为supports方法相当于做了类型判断
		FilterInvocation fi = (FilterInvocation) object;
		// 请求URL
		String url = fi.getRequestUrl();
		// 请求方法
		String httpMethod = fi.getRequest().getMethod();
		String key = httpMethod + "_" + url;
		return uriConfigMap.get(key);
	}

	@Override
	public Collection<ConfigAttribute> getAllConfigAttributes() {
		Set<ConfigAttribute> configs = new HashSet<>();
		uriConfigMap.forEach((k, v) -> {
			configs.addAll(v);
		});
		return configs;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return FilterInvocation.class.isAssignableFrom(clazz);
	}

}
