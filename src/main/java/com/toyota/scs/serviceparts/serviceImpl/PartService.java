package com.toyota.scs.serviceparts.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.toyota.scs.serviceparts.entity.PartEntity;
import com.toyota.scs.serviceparts.repository.PartRepository;
import com.toyota.scs.serviceparts.specification.PartSpecification;
import com.toyota.scs.serviceparts.util.SCSUtil;
@Service
public class PartService {

	@Autowired
	PartRepository partRepository;
	
	public Page getAllPart(Integer pageNo, Integer pageSize, String sortBy, String search){
		
        
        Pageable paging = PageRequest.of(pageNo, pageSize,SCSUtil.sortHelper(sortBy));
        
		Page<PartEntity> pagedResult ;
		
		if(search!=null&&!search.isEmpty()) {
			PartSpecification partSpec= new PartSpecification(search);			
			pagedResult=partRepository.findAll(partSpec,paging);
		}else {
			pagedResult = partRepository.findAll(paging);
		}
		
        
        return pagedResult;
	}
}
