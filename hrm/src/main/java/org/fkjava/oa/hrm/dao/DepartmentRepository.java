package org.fkjava.oa.hrm.dao;

import java.util.List;

import org.fkjava.oa.hrm.domain.Department;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, String> {

	Department findByNameAndParentNull(String name);

	Department findByNameAndParent(String name, Department parent);

	// 没有上级部门
	@Query("select max(number) from Department where parent is null")
	Double findMaxNumber();

	// 有上级部门
	@Query("select max(number) from Department where parent = :parent")
	Double findMaxNumberByParent(@Param("parent") Department parent);

	List<Department> findByParentNullOrderByNumberAsc();

	/**
	 * 上级部门为parent，排序号大于number参数
	 * 
	 * @param parent 上级部门
	 * @param number 查询的时候排序号大于此数字
	 * @return
	 */
	@Query("from Department d where d.parent = :parent and d.number > :number order by d.number asc")
	Page<Department> findNext(@Param("parent") Department parent, @Param("number") Double number, Pageable pageable);

	/**
	 * 上级部门为parent，排序号小于number参数
	 * 
	 * @param parent 上级部门
	 * @param number 查询的时候排序号小于此数字
	 * @return
	 */
	@Query("from Department d where d.parent = :parent and d.number < :number order by d.number desc")
	Page<Department> findPrev(@Param("parent") Department parent, @Param("number") Double number, Pageable pageable);

	@Query("from Department d where d.parent is null and d.number > :number order by d.number asc")
	Page<Department> findNextAndParentNull(Double number, Pageable pageable);

	@Query("from Department d where d.parent is null and d.number < :number order by d.number desc")
	Page<Department> findPrevAndParentNull(Double number, Pageable pageable);
}
