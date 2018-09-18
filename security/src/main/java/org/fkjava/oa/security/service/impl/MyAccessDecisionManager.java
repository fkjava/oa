package org.fkjava.oa.security.service.impl;

import java.util.Collection;
import java.util.Iterator;

import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityMessageSource;

// 自定义的决策器，判断用户是否具有访问权限
public class MyAccessDecisionManager implements AccessDecisionManager {

	protected MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();

	// 是用户具有的授权信息
	// object为访问的目标，通常是一个URL
	// configAttributes为根据URL找到的、访问该URL需要的权限/身份
	@Override
	public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes)
			throws AccessDeniedException, InsufficientAuthenticationException {

		// 遍历访问URL需要的权限
		Iterator<ConfigAttribute> ite = configAttributes.iterator();
		while (ite.hasNext()) {
			ConfigAttribute ca = ite.next();
			// 一个个需要的角色来进行判断
			String needRole = ((org.springframework.security.access.SecurityConfig) ca).getAttribute();

			// 检查用户的身份中，是否具有URL需要的角色，如果有则可以访问
			for (GrantedAuthority ga : authentication.getAuthorities()) {
				if (ga.getAuthority().equals(needRole)) {
					// 匹配到有对应角色,则允许通过
					return;
				}
			}
		}
		// 该URL有配置权限,但是当然登录用户没有匹配到对应权限,则禁止访问
		// 抛出异常就是禁止访问了！
		throw new AccessDeniedException(
				messages.getMessage("AbstractAccessDecisionManager.accessDenied", "Access is denied"));
	}

	// 后面两个方法暂时不用，所以直接返回true
	@Override
	public boolean supports(ConfigAttribute attribute) {
		return true;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return true;
	}
}
