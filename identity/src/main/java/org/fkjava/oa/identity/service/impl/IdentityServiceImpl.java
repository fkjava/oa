package org.fkjava.oa.identity.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.fkjava.oa.identity.dao.RoleDao;
import org.fkjava.oa.identity.dao.UserDao;
import org.fkjava.oa.identity.domain.Role;
import org.fkjava.oa.identity.domain.User;
import org.fkjava.oa.identity.domain.User.UserStatus;
import org.fkjava.oa.identity.service.IdentityService;
import org.fkjava.oa.identity.vo.Result;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
public class IdentityServiceImpl implements IdentityService, InitializingBean {

	@Autowired
	private RoleDao roleDao;
	@Autowired
	private UserDao userDao;
	@Autowired
	private PasswordEncoder passwordEncoder;

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
	@Transactional
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
		// 使用roleKey排序
		Sort sort = Sort.by(Order.asc("roleKey"));
		List<Role> roles = this.roleDao.findAll(sort);
		return roles;
	}

	@Override
	public void deleteRole(String id) {
		this.roleDao.deleteById(id);
	}

	@Transactional()
	@Override
	public void save(User user) {
		// 解决的问题：固定角色不要界面选择，直接加入！
		// 查询所有固定的Role，加入到User对象里面
		// 注意：如果有重复则要去掉！
		Set<Role> roles = this.roleDao.findByFixedTrue();
		// 根据用户传入的所有角色，把Role对象查询出来
		Set<String> ids = new HashSet<>();
		user.getRoles().forEach(role -> ids.add(role.getId()));
		List<Role> userRoles = this.roleDao.findAllById(ids);

		// 合并两个Role集合
		// 第一个集合是固定Role
		// 第二个集合是用户传入的Role
		// roles合并以后，就包含了：用户传入的Role、固定的Role，并且不会重复！
		roles.addAll(userRoles);

		// 把用户传入的Role集合，改为从数据库查询出来的Role集合，避免出现外键约束失败的问题
		user.getRoles().clear();
		user.getRoles().addAll(roles);

		if (StringUtils.isEmpty(user.getId())) {
			user.setId(null);
		}
		// 1.检查是否有id，如果有id表示修改，否则是新增
		// 2.不管是新增还是修改，都必须确保登录名是唯一键

		if (!StringUtils.isEmpty(user.getPassword())) {
			// 有密码的时候，把密码加密，需要把PasswordEncoder注入进来
			String encodedPassword = this.passwordEncoder.encode(user.getPassword());
			user.setPassword(encodedPassword);
		}
		if (user.getId() != null) {
			// 根据id找到现有的User对象，有部分数据可能未修改
			User oldUser = this.userDao.findById(user.getId()).get();
			if (StringUtils.isEmpty(user.getPassword())) {
				user.setPassword(oldUser.getPassword());
			}
			if (user.getStatus() == null) {
				user.setStatus(oldUser.getStatus());
			}
			// 修改
			User old = this.userDao.findByLoginName(user.getLoginName());
			if (old != null) {
				if (user.getId().equals(old.getId())) {
					// id相同，表示同一个用户，可以修改
					this.userDao.save(user);
				} else {
					throw new IllegalArgumentException("用户的登录名已经被其他用户占用，不能修改");
				}
			} else {
				this.userDao.save(user);
			}
		} else {
			// 新增
			// 1.要根据登录名查询User对象
			User old = this.userDao.findByLoginName(user.getLoginName());
			if (old == null) {
				// 用户的登录名未被占用
				user.setStatus(UserStatus.NORMAL);
				this.userDao.save(user);
			} else {
				throw new IllegalArgumentException("用户的登录名已经被其他用户占用，不能添加");
			}
		}
	}

	// 写完此方法以后，建议重构用户保存的判断，保证登录名的校验逻辑是一致的。
	@Override
	public Result checkLoginName(String loginName, String id) {
		Result result = new Result();
		if (StringUtils.isEmpty(id)) {
			// id为空表示新增用户，不需要判断id
			User old = this.userDao.findByLoginName(loginName);
			if (old == null) {
				result.setStatus(Result.STATUS_OK);
			} else {
				result.setStatus(Result.STATUS_ERROR);
			}
		} else {
			// 修改用户，要判断id
			User old = this.userDao.findByLoginName(loginName);
			if (old == null) {
				result.setStatus(Result.STATUS_OK);
			} else if (old.getId().equals(id)) {
				result.setStatus(Result.STATUS_WARN);
			} else {
				result.setStatus(Result.STATUS_ERROR);
			}
		}
		return result;
	}

	@Override
	public Page<User> findUsers(Integer pageNumber) {
		// 以登录名升序、以姓名降序
		Sort sort = Sort.by(Order.asc("loginName"), Order.desc("name"));
		Pageable pageable = PageRequest.of(pageNumber, 2, sort);
		// 如果条件复杂，需要自己扩展DAO
		Page<User> page = this.userDao.findAll(pageable);
		return page;
	}

	@Override
	public User findUserById(String id) {
		return this.userDao.findById(id).orElse(null);
	}

	@Override
	public Result separation(String id) {
		User user = this.userDao.findById(id).orElse(null);
		if (user != null) {
			user.setLoginName(null);
			user.setRoles(null);
			user.setStatus(UserStatus.SEPARATION);

			this.userDao.save(user);

			Result result = new Result();
			result.setMessage("操作成功");
			result.setStatus(Result.STATUS_OK);
			return result;
		} else {
			Result result = new Result();
			result.setMessage("非法请求，请从页面正常点击按钮来实现离职！");
			result.setStatus(Result.STATUS_ERROR);
			return result;
		}
	}

	@Override
	public Optional<User> findUserByLoginName(String loginName) {
		UserStatus status = UserStatus.SEPARATION;
		return this.userDao.findByLoginNameAndStatusNot(loginName, status);
	}
}
