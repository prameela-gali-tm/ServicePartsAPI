package com.toyota.scs.serviceparts.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.toyota.scs.serviceparts.entity.PartTransEntity;
import com.toyota.scs.serviceparts.repository.PartTransRepositroy;
import com.toyota.scs.serviceparts.specification.OrderSpecification;
import com.toyota.scs.serviceparts.specification.PartTransSpecification;
import com.toyota.scs.serviceparts.util.SCSUtil;

@Service
public class PartTransService {

	@Autowired
	PartTransRepositroy partTransRepositroy;
	
	public Page getAllPartTrans(Integer pageNo, Integer pageSize, String sortBy,String search){
        Pageable paging = PageRequest.of(pageNo, pageSize,SCSUtil.sortHelper(sortBy));
		 Page<PartTransEntity> pagedResult;
			
			if(search!=null&&!search.isEmpty()) {
				PartTransSpecification ordSpec= new PartTransSpecification(search);			
				pagedResult=partTransRepositroy.findAll(ordSpec,paging);
			}else {
				pagedResult = partTransRepositroy.findAll(paging);
			}
        
        return pagedResult;
	}
}
