package com.toyota.scs.serviceparts.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.toyota.scs.serviceparts.entity.VendorEntity;
import com.toyota.scs.serviceparts.repository.VendorRepositroy;

@Service
public class VendorService{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Autowired
	VendorRepositroy vendorRepositroy;
	
	public Page getAllVendor(Integer pageNo, Integer pageSize, String sortBy){
		Pageable paging = PageRequest.of(pageNo, pageSize,Sort.by(sortBy).ascending());
		 
        Page<VendorEntity> pagedResult = vendorRepositroy.findAll(paging);
        
        return pagedResult;
	}
}
