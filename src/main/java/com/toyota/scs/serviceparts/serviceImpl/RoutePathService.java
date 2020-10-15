package com.toyota.scs.serviceparts.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.toyota.scs.serviceparts.entity.RoutePathEntity;
import com.toyota.scs.serviceparts.repository.RoutePathRepositroy;

@Service
public class RoutePathService {
	
	@Autowired
	RoutePathRepositroy routePathRepositroy;
	
	public Page getAllRoutePathDetails(Integer pageNo, Integer pageSize, String sortBy){
		Pageable paging = PageRequest.of(pageNo, pageSize,Sort.by(sortBy).ascending());
		 
        Page<RoutePathEntity> pagedResult = routePathRepositroy.findAll(paging);
        
        return pagedResult;
	}

}
