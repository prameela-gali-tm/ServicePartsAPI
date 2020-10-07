package com.toyota.scs.serviceparts.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.toyota.scs.serviceparts.entity.ShipmentEntity;

public interface ShipmentRepositroy extends CrudRepository<ShipmentEntity, Long> ,PagingAndSortingRepository<ShipmentEntity, Long>{

}
