package org.fkjava.oa.hrm.dao;

import java.util.List;

import org.fkjava.oa.hrm.domain.Department;
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
}
