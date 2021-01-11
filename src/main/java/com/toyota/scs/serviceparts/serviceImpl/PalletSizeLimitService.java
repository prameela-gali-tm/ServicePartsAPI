package com.toyota.scs.serviceparts.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.toyota.scs.serviceparts.entity.PalletSizeLimitEntity;
import com.toyota.scs.serviceparts.repository.PalletSizeLimitRepositroy;
import com.toyota.scs.serviceparts.specification.OrderSpecification;
import com.toyota.scs.serviceparts.specification.PalletSizeLimitSpecification;

@Service
public class PalletSizeLimitService {

	@Autowired
	PalletSizeLimitRepositroy palletSizeLimitRepositroy;
	
	public Page getAllPalletSizeLimit(Integer pageNo, Integer pageSize, String sortBy,String search){
		 
        Pageable paging = PageRequest.of(pageNo, pageSize,Sort.by(sortBy).ascending());
        Page<PalletSizeLimitEntity> pagedResult;
		if(search!=null&&!search.isEmpty()) {
			PalletSizeLimitSpecification ordSpec= new PalletSizeLimitSpecification(search);			
			pagedResult=palletSizeLimitRepositroy.findAll(ordSpec,paging);
		}else {
			pagedResult = palletSizeLimitRepositroy.findAll(paging);
		}
    
        return pagedResult;
	}
}
