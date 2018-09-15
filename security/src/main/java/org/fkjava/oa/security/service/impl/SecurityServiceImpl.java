package org.fkjava.oa.security.service.impl;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;

import org.fkjava.oa.identity.domain.User;
import org.fkjava.oa.identity.service.IdentityService;
import org.fkjava.oa.security.service.SecurityService;
import org.fkjava.oa.security.vo.UserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SecurityServiceImpl implements SecurityService {

	@Autowired
	private IdentityService identityService;

	// 加载用户的信息，还未判断密码是否正确
	@Override
	// 一定要加上事务，否则会出现no Session的问题
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String loginName) throws UsernameNotFoundException {
		Optional<User> optional = this.identityService.findUserByLoginName(loginName);

		User user = optional.orElseThrow(() -> {
			return new UsernameNotFoundException("用户未找到");
		});

		// 用户拥有的角色
		// 在Spring Security里面，角色被称之为GrantedAuthority
		Collection<GrantedAuthority> authorities = new HashSet<>();
		user.getRoles().forEach(role -> {
			// 如果是角色，提交给Spring Security的时候，必须ROLE_开头
			SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + role.getRoleKey());
			authorities.add(authority);
		});

		// 把User对象，转换为UserDetails类的对象
		UserDetails ud = new UserDetails(user, authorities);

		return ud;
	}

}
