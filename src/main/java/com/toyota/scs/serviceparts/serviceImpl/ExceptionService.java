package com.toyota.scs.serviceparts.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.toyota.scs.serviceparts.entity.ExceptionEntity;
import com.toyota.scs.serviceparts.repository.ExceptionRepository;

@Service
public class ExceptionService {

	@Autowired
	ExceptionRepository exceptionRepository;
	
	public Page getAllException(Integer pageNo, Integer pageSize, String sortBy){
		 
        Pageable paging = PageRequest.of(pageNo, pageSize,Sort.by(sortBy).ascending());
		Page<ExceptionEntity> pagedResult = exceptionRepository.findAll(paging);
        
        return pagedResult;
	}
}
