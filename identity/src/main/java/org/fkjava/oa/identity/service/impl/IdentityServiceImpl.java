package org.fkjava.oa.identity.service.impl;

import java.util.List;
import java.util.Optional;

import org.fkjava.oa.identity.dao.RoleDao;
import org.fkjava.oa.identity.domain.Role;
import org.fkjava.oa.identity.service.IdentityService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
public class IdentityServiceImpl implements IdentityService, InitializingBean {

	@Autowired
	private RoleDao roleDao;

	@Override
	@Transactional
	public void afterPropertiesSet() throws Exception {
		// 初始化固定的角色，避免固定角色被删除！
		// KEY为USER的角色是固定的，不能删除、不能修改
		String key = "USER";
		Optional<Role> optional = this.roleDao.findByRoleKey(key);
		Role fixed = new Role();
		fixed.setFixed(true);
		fixed.setName("普通用户");
		fixed.setRoleKey(key);
		// 如果没有找到KEY为USER的Role，则创建一个固定的！
		Role role = optional.orElse(fixed);
		// 不管如何，保存一下，可以确保固定的角色总是存在的
		this.roleDao.save(role);
	}

	@Override
	public void save(Role role) {
		// 判断id是否存在，如果有id则表示修改；否则表示新增
		if (StringUtils.isEmpty(role.getId())) {
			// 新增，KEY如果已经存在，不能重复使用！
			Optional<Role> optional = this.roleDao.findByRoleKey(role.getRoleKey());
			Role old = optional.orElse(null);
			if (old == null) {
				role.setId(null);
				this.roleDao.save(role);
			} else {
				throw new IllegalArgumentException("角色的KEY不能重复！");
			}
		} else {
			// 修改，也要根据KEY查询数据库中的记录，判断ID是否和传入的ID相同。
			// 如果不同则表示数据库已有另外一条记录，使用了相同的KEY
			Optional<Role> optional = this.roleDao.findByRoleKey(role.getRoleKey());
			Role old = optional.orElse(null);
			if (old != null) {
				if (old.getId().equals(role.getId())) {
					// id相同，可以保存
					this.roleDao.save(role);
				} else {
					// key相同、id不同，不能保存
					throw new IllegalArgumentException("新的KEY和数据库已有的KEY重复，KEY不能重复！");
				}
			} else {
				// 没有相同的KEY，自然可以保存
				this.roleDao.save(role);
			}
		}
	}

	@Override
	public List<Role> findAllRoles() {
		Sort sort = Sort.by(Order.asc("roleKey"));
		List<Role> roles = this.roleDao.findAll(sort);
		return roles;
	}
	
	@Override
	public void deleteRole(String id) {
		this.roleDao.deleteById(id);
	}
}
