package com.toyota.scs.serviceparts.repository;

import org.springframework.data.repository.CrudRepository;

import com.toyota.scs.serviceparts.entity.VendorEntity;

public interface VendorRepositroy extends CrudRepository<VendorEntity, Long> {

		public VendorEntity findByVendorCodeEquals(String vendoreCode);
}
