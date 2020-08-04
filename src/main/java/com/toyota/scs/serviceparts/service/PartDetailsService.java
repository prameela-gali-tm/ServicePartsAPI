package com.toyota.scs.serviceparts.service;

import java.util.List;

import com.toyota.scs.serviceparts.entity.PartEntity;

public interface PartDetailsService {
	
	public List<PartEntity> findPartDetails(String partNumber,String vendorCode);
	
}
