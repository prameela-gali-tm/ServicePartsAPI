package com.toyota.scs.serviceparts.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.toyota.scs.serviceparts.entity.OrderEntity;

public interface OrderRepository extends CrudRepository<OrderEntity, Long>,PagingAndSortingRepository<OrderEntity, Long>,JpaSpecificationExecutor<OrderEntity> {

	public OrderEntity findByPoNumberAndVendorCode(String poNumber,String vendorCode);
	public OrderEntity findByPoNumberAndVendorCodeAndOrderType(String poNumber,String vendorCode,String orderType);
	public List<OrderEntity> findByVendorCode(String vendorCode);
	
}
