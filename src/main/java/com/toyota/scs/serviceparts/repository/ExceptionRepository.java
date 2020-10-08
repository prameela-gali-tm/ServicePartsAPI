package com.toyota.scs.serviceparts.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.toyota.scs.serviceparts.entity.ExceptionEntity;

public interface ExceptionRepository extends CrudRepository<ExceptionEntity, Long>,PagingAndSortingRepository<ExceptionEntity, Long>,JpaSpecificationExecutor<ExceptionEntity> {

}
