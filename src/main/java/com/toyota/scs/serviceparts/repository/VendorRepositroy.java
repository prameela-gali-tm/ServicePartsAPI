package com.toyota.scs.serviceparts.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.toyota.scs.serviceparts.entity.VendorEntity;

public interface VendorRepositroy extends JpaRepository<VendorEntity, Long> {

}
