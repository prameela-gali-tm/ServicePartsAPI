package com.toyota.scs.serviceparts.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.toyota.scs.serviceparts.entity.SerialNumberEntity;

public interface SerialNumberRepository
		extends CrudRepository<SerialNumberEntity, Long>, PagingAndSortingRepository<SerialNumberEntity, Long>, JpaSpecificationExecutor<SerialNumberEntity> {

	public List<SerialNumberEntity> findByPartTransId(long partTransId);
}
