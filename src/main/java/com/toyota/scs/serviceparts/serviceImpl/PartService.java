package com.toyota.scs.serviceparts.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.toyota.scs.serviceparts.entity.PartEntity;
import com.toyota.scs.serviceparts.repository.PartRepository;

@Service
public class PartService {

	@Autowired
	PartRepository partRepository;
	
	public Page getAllPart(Integer pageNo, Integer pageSize, String sortBy){
		Pageable paging = PageRequest.of(pageNo, pageSize,Sort.by(sortBy).ascending());
		 
        Page<PartEntity> pagedResult = partRepository.findAll(paging);
        
        return pagedResult;
	}
}
