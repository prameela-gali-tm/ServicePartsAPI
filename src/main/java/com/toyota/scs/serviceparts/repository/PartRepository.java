package com.toyota.scs.serviceparts.repository;

import java.util.Date;

import org.springframework.data.repository.CrudRepository;

import com.toyota.scs.serviceparts.entity.PartEntity;

public interface PartRepository extends CrudRepository<PartEntity, Long> {

	public PartEntity findByPartNumberAndLineItemNumberAndDeliveryDueDate(String partNumber,String lineItemNumber,Date deliveryDueDate);
}
