package com.toyota.scs.serviceparts.repository;

import org.springframework.data.repository.CrudRepository;

import com.toyota.scs.serviceparts.entity.CaseEntity;

public interface CaseRepositroy extends CrudRepository<CaseEntity, Long> {

	public CaseEntity findByCaseNumber(String caseNumber);
}
