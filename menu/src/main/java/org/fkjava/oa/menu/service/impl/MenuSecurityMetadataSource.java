package org.fkjava.oa.menu.service.impl;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.fkjava.oa.identity.domain.Role;
import org.fkjava.oa.menu.dao.MenuRepository;
import org.fkjava.oa.menu.domain.Menu;
import org.fkjava.oa.security.vo.MenuSecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.AntPathMatcher;

@Service
public class MenuSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

	@Autowired
	private MenuRepository menuRepository;

	// 每个请求要判断权限的时候，都会调用此方法来获取访问需要的权限
	@Override
	public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
		// 传入的object就是请求的相关参数
//		System.out.println("类型：" + object);
//		System.out.println("类对象：" + object.getClass());
		FilterInvocation invocation = (FilterInvocation) object;
		// URL+method是一个唯一键
		String url = invocation.getRequestUrl();
		String method = invocation.getRequest().getMethod();

		// Spring提供路径匹配器
		AntPathMatcher matcher = new AntPathMatcher();

		Collection<ConfigAttribute> result = new LinkedList<>();
		// 获取所有的URL和它需要的权限
		Collection<ConfigAttribute> allAttributes = this.getAllConfigAttributes();

//		allAttributes.forEach(c -> {
//			MenuSecurityConfig config = (MenuSecurityConfig) c;
//			// 判断当前的访问method跟配置第method是否相同，如果相同则匹配URL
//			if (config.getMethod().equals(method)) {
//				// 使用Spring提供的路径匹配器来检查URL是否匹配
//				// 支持匹配完整路径、通配符
//				if (matcher.match(config.getUrl(), url)) {
//					result.add(config);
//				}
//			}
//		});

//		Stream<ConfigAttribute> stream = allAttributes.stream().filter((c) -> {
//			MenuSecurityConfig config = (MenuSecurityConfig) c;
//			// 判断当前的访问method跟配置第method是否相同，如果相同则匹配URL
//			if (config.getMethod().equals(method)) {
//				// 使用Spring提供的路径匹配器来检查URL是否匹配
//				// 支持匹配完整路径、通配符
//				return matcher.match(config.getUrl(), url);
//			}
//			return false;
//		});
//
//		stream.forEach(config -> result.add(config));

		// GET /identity/user
		allAttributes.stream().filter((c) -> {
			MenuSecurityConfig config = (MenuSecurityConfig) c;
			return config.getMethod().equals(method) && matcher.match(config.getUrl(), url);
		}).forEach(result::add);

		// 加入默认的角色配置
		MenuSecurityConfig defaultConfig = new MenuSecurityConfig(url, method, "ROLE_USER");
		if (!result.contains(defaultConfig)) {
			result.add(defaultConfig);
		}

//		System.out.println(result);

		// 返回null或者空集合，表示访问此URL不需要任何权限
		return result;
	}

	// 此方法可以想办法加入缓存中，加入缓存以后，就不需要每个URL都查询一次。
	// 可以直接简单设置缓存的过期事件为30秒，表示每30秒从数据库更新一次。
	@Override
	@Transactional(readOnly = true)
	public Collection<ConfigAttribute> getAllConfigAttributes() {
		Collection<ConfigAttribute> result = new LinkedList<>();

		// 获取所有安全配置（URL、method、权限）
		List<Menu> menus = this.menuRepository.findAll();

		// 要把Menu转换为MenuSecurityConfig对象，一个角色、一个URL、一个method对应一个MenuSecurityConfig
		menus.forEach(menu -> {
			List<Role> roles = menu.getRoles();
			roles.forEach(role -> {
				// 创建自定义的ConfigAttribute对象
				MenuSecurityConfig config = //
						new MenuSecurityConfig(menu.getUrl(), menu.getMethod(), "ROLE_" + role.getRoleKey());
				result.add(config);
			});
		});

		return result;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		// 判断支持哪种方式的安全验证，简单的做法，直接返回true
		return true;
	}

}
