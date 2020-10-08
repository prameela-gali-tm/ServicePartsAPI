package com.toyota.scs.serviceparts.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.toyota.scs.serviceparts.entity.RoutePathNodeEntity;

public interface RoutePathNodeRepositroy extends CrudRepository<RoutePathNodeEntity, Long>,PagingAndSortingRepository<RoutePathNodeEntity, Long>,JpaSpecificationExecutor<RoutePathNodeEntity> {

}
