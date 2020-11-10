package com.toyota.scs.serviceparts.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.toyota.scs.serviceparts.entity.ShipmentEntity;
@Repository
@Transactional
public interface ShipmentRepositroy extends CrudRepository<ShipmentEntity, Long> ,PagingAndSortingRepository<ShipmentEntity, Long>,JpaSpecificationExecutor<ShipmentEntity>{
	
	
	
//	@Query(value="SELECT s from shipment s where s.vendorCodes IN(:vendorCode) and s.status =:DRAFT and s.status =:SHIPMENT LOAD",countQuery="select ")       // using @query
	    Page<ShipmentEntity> findAllByVendorCodeInAndStatusIn(List<String> vendorCd,List<String> status,Pageable paging);
}
