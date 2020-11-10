package com.toyota.scs.serviceparts.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.toyota.scs.serviceparts.entity.CaseEntity;
import com.toyota.scs.serviceparts.repository.CaseRepositroy;

@Service
public class CaseService {

	@Autowired
	CaseRepositroy caseRepositroy;
	
	public Page getAllCase(Integer pageNo, Integer pageSize, String sortBy){
		Pageable paging = PageRequest.of(pageNo, pageSize,Sort.by(sortBy).ascending());
		 
        Page<CaseEntity> pagedResult = caseRepositroy.findAll(paging);
        
        return pagedResult;
	}
	
}
