package org.fkjava.oa.security.service.impl;

import java.util.Collection;

import org.fkjava.oa.security.vo.MenuSecurityConfig;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.stereotype.Service;

@Service
public class MyAccessDecisionManager implements AccessDecisionManager {

	protected MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();

	// authentication就是当前登录用户的信息
	// object是访问目标
	// configAttributes就是根据object在元数据中获取出来的集合
	// 如果不匹配，抛出AccessDeniedException异常
	@Override
	public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes)
			throws AccessDeniedException, InsufficientAuthenticationException {

		// 循环所有的用户角色，判断是否在configAttributes里面，如果在表示可以访问，否则抛出异常。
		// authorities是用户有用的权限
		// configAttributes配置到数据库里面，访问URL/操作需要具备的权限
		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

		for (GrantedAuthority authority : authorities) {
			for (ConfigAttribute config : configAttributes) {
				MenuSecurityConfig c = (MenuSecurityConfig) config;
				if (c.getAttribute().equals(authority.getAuthority())) {
					// 有权访问
					return;
				}
			}
		}
		throw new AccessDeniedException(
				messages.getMessage("AbstractAccessDecisionManager.accessDenied", "Access is denied"));
	}

	@Override
	public boolean supports(ConfigAttribute attribute) {
		if (attribute instanceof MenuSecurityConfig) {
			return true;
		}
		return false;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return true;
	}

}
