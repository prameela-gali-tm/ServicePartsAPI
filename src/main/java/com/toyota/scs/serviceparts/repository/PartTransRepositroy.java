package com.toyota.scs.serviceparts.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.toyota.scs.serviceparts.entity.PartTransEntity;

public interface PartTransRepositroy extends CrudRepository<PartTransEntity, Long>,PagingAndSortingRepository<PartTransEntity, Long>,JpaSpecificationExecutor<PartTransEntity> {

	PartTransEntity findByPartId(long partId);
	PartTransEntity findByCaseIdAndPartId(long caseid,long partId);
	List<PartTransEntity> findByCaseId(long caseId);
}
