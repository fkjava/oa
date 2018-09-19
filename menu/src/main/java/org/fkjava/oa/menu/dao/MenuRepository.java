package org.fkjava.oa.menu.dao;

import java.util.List;

import org.fkjava.oa.menu.domain.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuRepository extends JpaRepository<Menu, String> {

	Menu findByNameAndParentNull(String name);

	Menu findByNameAndParent(String name, Menu parent);

	List<Menu> findByParentNull();

}
