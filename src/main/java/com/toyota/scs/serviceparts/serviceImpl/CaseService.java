package com.toyota.scs.serviceparts.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.toyota.scs.serviceparts.entity.CaseEntity;
import com.toyota.scs.serviceparts.entity.OrderEntity;
import com.toyota.scs.serviceparts.repository.CaseRepositroy;
import com.toyota.scs.serviceparts.specification.CaseSpecification;
import com.toyota.scs.serviceparts.specification.OrderSpecification;

@Service
public class CaseService {

	@Autowired
	CaseRepositroy caseRepositroy;
	
	public Page getAllCase(Integer pageNo, Integer pageSize, String sortBy,String search){
		Pageable paging = PageRequest.of(pageNo, pageSize,Sort.by(sortBy).ascending());
		 
		Page<CaseEntity> pagedResult ;
		
		if(search!=null&&!search.isEmpty()) {
			CaseSpecification ordSpec= new CaseSpecification(search);			
			pagedResult=caseRepositroy.findAll(ordSpec,paging);
		}else {
			pagedResult = caseRepositroy.findAll(paging);
		}
		
        
        return pagedResult;
	}
	
}
