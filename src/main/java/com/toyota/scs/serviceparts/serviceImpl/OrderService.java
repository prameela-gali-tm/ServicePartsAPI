package com.toyota.scs.serviceparts.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.toyota.scs.serviceparts.entity.OrderEntity;
import com.toyota.scs.serviceparts.repository.OrderRepositroy;

@Service
public class OrderService {

	@Autowired
	OrderRepositroy repositroy;
	
	public Page getAllOrder(Integer pageNo, Integer pageSize, String sortBy){
		 
        Pageable paging = PageRequest.of(pageNo, pageSize,Sort.by(sortBy).ascending());
		Page<OrderEntity> pagedResult = repositroy.findAll(paging);
        
        return pagedResult;
	}
}
