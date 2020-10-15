package com.toyota.scs.serviceparts.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.toyota.scs.serviceparts.entity.RoutePathEntity;

public interface RoutePathRepositroy extends CrudRepository<RoutePathEntity, Long>,PagingAndSortingRepository<RoutePathEntity, Long>,JpaSpecificationExecutor<RoutePathEntity> {

}
