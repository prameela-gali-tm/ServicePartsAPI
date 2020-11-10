package com.toyota.scs.serviceparts.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.toyota.scs.serviceparts.entity.CaseEntity;

public interface CaseRepositroy extends CrudRepository<CaseEntity, Long>,PagingAndSortingRepository<CaseEntity, Long>,JpaSpecificationExecutor<CaseEntity> {

	public CaseEntity findByCaseNumber(String caseNumber);
}
