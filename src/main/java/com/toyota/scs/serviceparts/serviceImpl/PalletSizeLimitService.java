package com.toyota.scs.serviceparts.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.toyota.scs.serviceparts.entity.PalletSizeLimitEntity;
import com.toyota.scs.serviceparts.repository.PalletSizeLimitRepositroy;

@Service
public class PalletSizeLimitService {

	@Autowired
	PalletSizeLimitRepositroy palletSizeLimitRepositroy;
	
	public Page getAllPalletSizeLimit(Integer pageNo, Integer pageSize, String sortBy){
		 
        Pageable paging = PageRequest.of(pageNo, pageSize,Sort.by(sortBy).ascending());
		Page<PalletSizeLimitEntity> pagedResult = palletSizeLimitRepositroy.findAll(paging);
        
        return pagedResult;
	}
}
