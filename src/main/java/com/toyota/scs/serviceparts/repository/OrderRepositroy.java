package com.toyota.scs.serviceparts.repository;

import org.springframework.data.repository.CrudRepository;

import com.toyota.scs.serviceparts.entity.OrderEntity;

public interface OrderRepositroy extends CrudRepository<OrderEntity, Long> {

	public OrderEntity findByPoNumberAndVendorCode(String poNumber,String vendorCode);
	
}
