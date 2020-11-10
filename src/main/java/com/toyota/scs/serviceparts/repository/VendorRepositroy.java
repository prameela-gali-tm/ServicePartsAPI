package com.toyota.scs.serviceparts.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.toyota.scs.serviceparts.entity.VendorEntity;

public interface VendorRepositroy extends PagingAndSortingRepository<VendorEntity, Long>,CrudRepository<VendorEntity, Long>,JpaSpecificationExecutor<VendorEntity> {

		public VendorEntity findByVendorCodeEquals(String vendoreCode);
		//Iterable<VendorEntity> findAll(Sort sort);
		//Page<VendorEntity> findAll(Pageable pageable);
}
