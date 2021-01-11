package com.toyota.scs.serviceparts.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.toyota.scs.serviceparts.entity.ExceptionEntity;
import com.toyota.scs.serviceparts.repository.ExceptionRepository;
import com.toyota.scs.serviceparts.specification.ExceptionSpecification;
import com.toyota.scs.serviceparts.specification.OrderSpecification;

@Service
public class ExceptionService {

	@Autowired
	ExceptionRepository exceptionRepository;
	
	public Page getAllException(Integer pageNo, Integer pageSize, String sortBy,String search){
		 
        Pageable paging = PageRequest.of(pageNo, pageSize,Sort.by(sortBy).ascending());
        Page<ExceptionEntity> pagedResult;
        
        if(search!=null&&!search.isEmpty()) {
			ExceptionSpecification ordSpec= new ExceptionSpecification(search);			
			pagedResult=exceptionRepository.findAll(ordSpec,paging);
		}else {
			pagedResult = exceptionRepository.findAll(paging);
		}
		        
        
        
        return pagedResult;
	}
}
