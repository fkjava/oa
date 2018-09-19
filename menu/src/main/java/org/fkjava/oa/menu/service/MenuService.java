package org.fkjava.oa.menu.service;

import java.util.List;

import org.fkjava.oa.menu.domain.Menu;

public interface MenuService {

	void save(Menu menu);

	List<Menu> findAllMenus();

	List<Menu> findCurrentUserMenuTree();

}
