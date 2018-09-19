package org.fkjava.oa.menu.dao;

import java.util.List;

import org.fkjava.oa.identity.domain.Role;
import org.fkjava.oa.menu.domain.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuRepository extends JpaRepository<Menu, String> {

	Menu findByNameAndParentNull(String name);

	Menu findByNameAndParent(String name, Menu parent);

	List<Menu> findByParentNullOrderByName();

//	// 可以使用@Query注解自定义查询语句
//	@Query(value = "select m from Menu m " //
//			+ "where m.roles in ( ?1 ) "//
//			+ "and m.parent in (select p from Menu p where p.parent is null)")
//	List<Menu> find(Collection<Role> roles);

	List<Menu> findByRolesIn(List<Role> roles);
}
