package org.fkjava.oa.menu.service.impl;

import java.util.List;

import org.fkjava.oa.menu.dao.MenuRepository;
import org.fkjava.oa.menu.domain.Menu;
import org.fkjava.oa.menu.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
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
		return this.menuRepository.findByParentNull();
	}
}
