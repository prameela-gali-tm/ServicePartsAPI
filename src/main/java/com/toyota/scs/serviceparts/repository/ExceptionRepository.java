package com.toyota.scs.serviceparts.repository;

import org.springframework.data.repository.CrudRepository;

import com.toyota.scs.serviceparts.entity.ExceptionEntity;

public interface ExceptionRepository extends CrudRepository<ExceptionEntity, Long> {

}
