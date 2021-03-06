package org.egov.egf.persistence.repository;

import org.egov.egf.persistence.entity.EgfStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface EgfStatusJpaRepository
		extends JpaRepository<EgfStatus, java.lang.Long>, JpaSpecificationExecutor<EgfStatus> {

}