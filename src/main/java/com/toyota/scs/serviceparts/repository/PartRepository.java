package com.toyota.scs.serviceparts.repository;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.toyota.scs.serviceparts.entity.PartEntity;

public interface PartRepository extends CrudRepository<PartEntity, Long>,PagingAndSortingRepository<PartEntity, Long>,JpaSpecificationExecutor<PartEntity> {

	public PartEntity findByPartNumberAndLineItemNumberAndDeliveryDueDate(String partNumber,String lineItemNumber,Date deliveryDueDate);
	
}
