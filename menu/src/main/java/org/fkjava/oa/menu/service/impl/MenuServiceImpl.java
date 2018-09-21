package org.fkjava.oa.menu.service.impl;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.fkjava.oa.commons.vo.Result;
import org.fkjava.oa.menu.dao.MenuRepository;
import org.fkjava.oa.menu.domain.Menu;
import org.fkjava.oa.menu.service.MenuService;
import org.fkjava.oa.security.vo.UserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class MenuServiceImpl implements MenuService {

	@Autowired
	private MenuRepository menuRepository;

	@Override
	public void save(Menu menu) {
		// 1.检查数据的完整性，特别关联的对象如果没有id就要设置为空
		if (menu.getParent() != null && StringUtils.isEmpty(menu.getParent().getId())) {
			// 有parent对象，但是id为空，无法关联
			// 此时要把parent设置null
			menu.setParent(null);
		}
		if (StringUtils.isEmpty(menu.getId())) {
			menu.setId(null);
		}

		// 2.判断是否有parent，如果有parent，表示当前Menu对象为下级菜单，需要根据parent检查是否有同名的菜单
		// 如果没有parent，则表示没有上级菜单，当前Menu自己就是第一级菜单，查询parent为null的菜单是否有同名的
		Menu old;
		if (menu.getParent() == null) {
			// 没有上级菜单
			// from Menu where name ?1 and parent is null
			old = this.menuRepository.findByNameAndParentNull(menu.getName());
		} else {
			// 有上级菜单
			// from Menu where name ?1 and parent ?2
			old = this.menuRepository.findByNameAndParent(menu.getName(), menu.getParent());
		}

		if (old == null) {
			// 没有重名的，直接保存新的对象
			this.menuRepository.save(menu);
		} else if (menu.getId() != null && menu.getId().equals(old.getId())) {
			// 有重名的，但是id相同，表示同一个菜单，直接更新
			this.menuRepository.save(menu);
		} else {
			// 非法参数：重名的
			throw new IllegalArgumentException("同级的菜单不能重名");
		}
	}

	@Override
	public List<Menu> findAllMenus() {
		// 只把一级菜单查询出来
		return this.menuRepository.findByParentNullOrderByName();
	}

	@Override
	public List<Menu> findCurrentUserMenuTree() {
		// 1.获取当前用户，Spring Security在登录以后，会把当前的用户信息，存储在SecurityContextHolder里面。
		// 存储的本质，是ThreadLocal类型的对象，ThreadLocal是一个线程隔离的对象，存储在里面的数据仅在当前线程有效。
		UserDetails ud = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		// 2.根据用户的角色查询所有菜单
		List<Menu> allTopMenus = this.menuRepository.findByParentNullOrderByName();// 全部一级菜单
		List<Menu> subMenus = this.menuRepository.findByRolesIn(ud.getRoles());// 全部有权限的菜单
		// 把有权限的菜单里面，要过滤掉上级菜单不是在allTopMenus集合里面的
		// 只要Parent对象在allTopMenus的二级菜单
		List<Menu> newSubMenus = new LinkedList<>();
		subMenus.stream().filter(sm -> {
			for (Menu p : allTopMenus) {
				if (sm.getParent() != null// 子菜单要有上级
						// 子菜单的上级的id跟一级菜单的id相同
						&& sm.getParent().getId().equals(p.getId())) {
					// 返回true表示需要！
					return true;
				}
			}
			// 返回false表示不要的
			return false;
		})//
				.forEach(m -> newSubMenus.add(m));

		// 3.整理得到的二级菜单，把一级菜单也获取出来
		// 不要直接把持久化对象传到Session（菜单要存储在Session）
		// 所以在这里的过程中要创建新的Menu对象（脱离Hibernate管理）
		// 以一级菜单的id为key、value是一级菜单
		// 但是在一级菜单里面，有child属性包含二级菜单
		Map<String, Menu> menus = new HashMap<>();
		newSubMenus.forEach(m -> {
			Menu parent = m.getParent();
			String parentId = parent.getId();
			if (!menus.containsKey(parentId)) {
				// 创建新的菜单
				Menu newParent = new Menu();
				newParent.setChild(new LinkedList<>());
				newParent.setId(parentId);
				newParent.setMethod(parent.getMethod());
				newParent.setName(parent.getName());
				newParent.setUrl(parent.getUrl());
				// 经过这次循环以后，menus里面全部都是一级菜单，而且不会重复
				menus.put(parentId, newParent);
			}
		});

		newSubMenus.forEach(m -> {
			// 直接在menus集合里面获取一级菜单
			String parentId = m.getParent().getId();
			Menu parent = menus.get(parentId);

			Menu newChild = new Menu();
			newChild.setId(m.getId());
			newChild.setMethod(m.getMethod());
			newChild.setName(m.getName());
			newChild.setUrl(m.getUrl());

			// 经过此循环，所有二级菜单都会放入对应的一级菜单里面
			parent.getChild().add(newChild);
		});

		List<Menu> result = new LinkedList<>();
		result.addAll(menus.values());

		result.sort((v1, v2) -> {
			return v1.getName().compareTo(v2.getName());
		});

		// 把结果设置为不可改变的list
		result.forEach(parent -> {
			parent.getChild().sort((v1, v2) -> {
				return v1.getName().compareTo(v2.getName());
			});
			parent.setChild(Collections.unmodifiableList(parent.getChild()));
		});
		result = Collections.unmodifiableList(result);

		return result;
	}

	@Override
	public Result delete(String id) {
		Result result = new Result();
		// 1.根据id把菜单查询出来
		Menu menu = this.menuRepository.findById(id).orElse(null);
		if (menu != null) {
			// 2.判断菜单是否有关联的Role，如果有，不能删除
			if (menu.getRoles().isEmpty()) {
				this.menuRepository.delete(menu);
				result.setMessage("删除成功");
				result.setStatus(Result.STATUS_OK);
			} else {
				result.setMessage("删除失败，有关联的角色");
				result.setStatus(Result.STATUS_ERROR);
			}
		} else {
			result.setMessage("删除成功");
			result.setStatus(Result.STATUS_OK);
		}
		return result;
	}
}
