package com.toyota.scs.serviceparts.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.toyota.scs.serviceparts.entity.PalletSizeLimitEntity;

public interface PalletSizeLimitRepositroy extends CrudRepository<PalletSizeLimitEntity, Long>,PagingAndSortingRepository<PalletSizeLimitEntity, Long>,JpaSpecificationExecutor<PalletSizeLimitEntity> {

}
