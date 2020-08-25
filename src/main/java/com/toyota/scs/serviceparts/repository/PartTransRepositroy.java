package com.toyota.scs.serviceparts.repository;

import org.springframework.data.repository.CrudRepository;

import com.toyota.scs.serviceparts.entity.PartTransEntity;

public interface PartTransRepositroy extends CrudRepository<PartTransEntity, Long> {

	PartTransEntity findByPartId(long partId);
	PartTransEntity findByCaseId(long caseid);
}
