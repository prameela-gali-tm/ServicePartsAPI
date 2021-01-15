package com.toyota.scs.serviceparts.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.toyota.scs.serviceparts.entity.OrderEntity;
import com.toyota.scs.serviceparts.repository.OrderRepository;
import com.toyota.scs.serviceparts.specification.OrderSpecification;
import com.toyota.scs.serviceparts.util.SCSUtil;

@Service
public class OrderService {

	@Autowired
	OrderRepository repositroy;
	
	public Page getAllOrder(Integer pageNo, Integer pageSize, String sortBy, String search){
		 
        Pageable paging = PageRequest.of(pageNo, pageSize,SCSUtil.sortHelper(sortBy));
        
		Page<OrderEntity> pagedResult ;
		
		if(search!=null&&!search.isEmpty()) {
			OrderSpecification ordSpec= new OrderSpecification(search);			
			pagedResult=repositroy.findAll(ordSpec,paging);
		}else {
			pagedResult = repositroy.findAll(paging);
		}
		
        
        return pagedResult;
	}
}
