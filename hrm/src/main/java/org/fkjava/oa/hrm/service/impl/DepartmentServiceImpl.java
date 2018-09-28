package org.fkjava.oa.hrm.service.impl;

import java.util.List;

import org.fkjava.oa.commons.vo.Result;
import org.fkjava.oa.hrm.dao.DepartmentRepository;
import org.fkjava.oa.hrm.domain.Department;
import org.fkjava.oa.hrm.service.DepartmentService;
import org.fkjava.oa.identity.dao.UserDao;
import org.fkjava.oa.identity.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class DepartmentServiceImpl implements DepartmentService {

	@Autowired
	private DepartmentRepository departmentRepository;
	@Autowired
	private UserDao userDao;

	@Override
	public void save(Department dept) {
		if (StringUtils.isEmpty(dept.getId())) {
			dept.setId(null);
		}
		if (dept.getParent() != null && StringUtils.isEmpty(dept.getParent().getId())) {
			dept.setParent(null);
		}
		if (dept.getOwner() != null && StringUtils.isEmpty(dept.getOwner().getId())) {
			dept.setOwner(null);
		}

		// 1.检查当前父部门下面，是否有同名部门
		Department old;
		if (dept.getParent() == null) {
			old = this.departmentRepository.findByNameAndParentNull(dept.getName());
		} else {
			old = this.departmentRepository.findByNameAndParent(dept.getName(), dept.getParent());
		}

		// 2.根据owner找到对应的User对象作为负责人
		User owner = null;
		if (dept.getOwner() != null) {
			owner = this.userDao.findById(dept.getOwner().getId()).orElse(null);
		}
		dept.setOwner(owner);

		// 3.查询排序号：获取当前父部门下，最大的排序号并加10000，如果没有则为0
		if (dept.getId() != null) {
			// 修改的时候不要改排序号
			Department old2 = this.departmentRepository.findById(dept.getId()).orElse(dept);
			dept.setNumber(old2.getNumber());
		} else {
			// 查询最大的排序号，并且加上10000
			Double number;
			if (dept.getParent() == null) {
				number = this.departmentRepository.findMaxNumber();
			} else {
				number = this.departmentRepository.findMaxNumberByParent(dept.getParent());
			}
			if (number == null) {
				number = 0.0;
			} else {
				number = number + 10000;
			}
			dept.setNumber(number);
		}

		// 4.保存数据
		if ((old != null && old.getId().equals(dept.getId())) || old == null) {
			// 名字相同、id相同，修改
			// 或者没有同名的
			this.departmentRepository.save(dept);
		} else {
			// 有同名的、并且id不同
			throw new IllegalArgumentException("部门的名称不能同名");
		}
	}

	@Override
	public List<Department> findTopDepartments() {
		return this.departmentRepository.findByParentNullOrderByNumberAsc();
	}

	@Override
	public Result deleteById(String id) {
		Result result;
		// 严格来讲，如果有员工在部门里面，肯定是不能删除的！
		try {
			this.departmentRepository.deleteById(id);
			result = Result.of(Result.STATUS_OK, "删除成功");
		} catch (Exception ex) {
			result = Result.of(Result.STATUS_ERROR, "删除失败");
		}
		return result;
	}

	@Override
	public Result move(String moveType, String departmentId, String targetDepartmentId) {

		// 查询要移动的部门
		Department department = this.departmentRepository.findById(departmentId).orElse(null);
		Department target = null;
		if (StringUtils.isEmpty(targetDepartmentId)) {
			// 没有目标，将会作为新的根节点放到最后面
			targetDepartmentId = null;
		} else {
			// 把目标部门也查询出来
			target = this.departmentRepository.findById(targetDepartmentId).orElse(null);
		}
		if (target == null) {
			// 移动部门为一级部门
			department.setParent(null);
			Double number = this.departmentRepository.findMaxNumber();
			if (number == null) {
				number = 0.0;
			} else {
				number = number + 10000;
			}
			department.setNumber(number);
		} else {
			// 字符串常量放到前面比较安全，不容易出现空指针
			if ("inner".equals(moveType)) {
				// 作为子部门
				Double maxNumber = this.departmentRepository.findMaxNumberByParent(target);
				if (maxNumber == null) {
					maxNumber = 0.0;
				} else {
					maxNumber = maxNumber + 10000;
				}
				department.setNumber(maxNumber);

				// 作为target的子节点
				department.setParent(target);
			} else if ("next".equals(moveType) || "prev".equals(moveType)) {
				// 放到target之后
				// 获取parent出来
				Department parent = target.getParent();

				// 查询比target的number还要大的一个节点
				// 查询同级部门、数字大于number的节点，并且只要一个节点
				Pageable pageable = PageRequest.of(0, 1);
				Page<Department> nextPage;
				if ("next".equals(moveType)) {
					if (parent == null) {
						nextPage = this.departmentRepository.findNextAndParentNull(target.getNumber(), pageable);
					} else {
						nextPage = this.departmentRepository.findNext(parent, target.getNumber(), pageable);
					}
				} else {
					if (parent == null) {
						nextPage = this.departmentRepository.findPrevAndParentNull(target.getNumber(), pageable);
					} else {
						nextPage = this.departmentRepository.findPrev(parent, target.getNumber(), pageable);
					}
				}
				Double number;
				if (nextPage.getNumberOfElements() > 0) {
					// 有下一条记录，把前后两个数字相加求平均
					number = (target.getNumber() + nextPage.getContent().get(0).getNumber()) / 2;
				} else {
					// 没有下一条记录
					if ("next".equals(moveType)) {
						number = target.getNumber() + 10000;
					} else {
						number = target.getNumber() - 10000;
					}
				}
				department.setNumber(number);
				department.setParent(parent);
//		} else if ("prev".equals(moveType)) {
//			Department parent = target.getParent();
//			// 放到target之前
//			Pageable pageable = PageRequest.of(0, 1);
//			Page<Department> nextPage = this.departmentRepository.findPrev(parent, target.getNumber(), pageable);
//			Double number;
//			if (nextPage.getNumberOfElements() > 0) {
//				// 有下一条记录，把前后两个数字相加求平均
//				number = (target.getNumber() + nextPage.getContent().get(0).getNumber()) / 2;
//			} else {
//				// 没有下一条记录
//				number = target.getNumber() - 10000;
//			}
//			department.setNumber(number);
//			department.setParent(parent);
			}
		}
		this.departmentRepository.save(department);

		Result result = Result.of(Result.STATUS_OK, "移动成功");
		return result;
	}
}
