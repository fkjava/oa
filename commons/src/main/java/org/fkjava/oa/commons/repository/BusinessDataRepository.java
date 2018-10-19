package org.fkjava.oa.commons.repository;

import org.fkjava.oa.commons.domain.BusinessData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BusinessDataRepository<T extends BusinessData> //
		extends JpaRepository<T, String> {

}
