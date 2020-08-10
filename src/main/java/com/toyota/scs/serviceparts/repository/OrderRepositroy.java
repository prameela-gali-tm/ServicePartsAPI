package com.toyota.scs.serviceparts.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.toyota.scs.serviceparts.entity.OrderEntity;

public interface OrderRepositroy extends CrudRepository<OrderEntity, Long> {

	public OrderEntity findByPoNumberAndVendorCode(String poNumber,String vendorCode);
	public List<OrderEntity> findByVendorCode(String vendorCode);
	
}
