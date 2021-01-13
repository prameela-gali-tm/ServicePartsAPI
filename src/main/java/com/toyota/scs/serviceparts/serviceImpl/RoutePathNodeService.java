package com.toyota.scs.serviceparts.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.toyota.scs.serviceparts.entity.RoutePathNodeEntity;
import com.toyota.scs.serviceparts.repository.RoutePathNodeRepositroy;
import com.toyota.scs.serviceparts.specification.OrderSpecification;
import com.toyota.scs.serviceparts.specification.RoutePathNodeSpecification;
import com.toyota.scs.serviceparts.util.SCSUtil;

@Service
public class RoutePathNodeService {

	@Autowired
	RoutePathNodeRepositroy nodeRepositroy;

	public Page getAllRoutePathNodeDetails(Integer pageNo, Integer pageSize, String sortBy, String search) {
        Pageable paging = PageRequest.of(pageNo, pageSize,SCSUtil.sortHelper(sortBy));
		Page<RoutePathNodeEntity> pagedResult;

		if (search != null && !search.isEmpty()) {
			RoutePathNodeSpecification ordSpec = new RoutePathNodeSpecification(search);
			pagedResult = nodeRepositroy.findAll(ordSpec, paging);
		} else {
			pagedResult = nodeRepositroy.findAll(paging);
		}

		return pagedResult;
	}
}
