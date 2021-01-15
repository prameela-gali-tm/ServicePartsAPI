package com.toyota.scs.serviceparts.specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.toyota.scs.serviceparts.entity.RoutePathNodeEntity;
import com.toyota.scs.serviceparts.util.SCSUtil;

public class RoutePathNodeSpecification  implements Specification<RoutePathNodeEntity>{

	
	private String search;
	
	
	public RoutePathNodeSpecification(String search) {
		super();
		this.search = search;
	}


	public RoutePathNodeSpecification() {
		super();
		// TODO Auto-generated constructor stub
	}


	@Override
	public Predicate toPredicate(Root<RoutePathNodeEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
		
		
			
			 return SCSUtil.toPredicate(this.search, root, query, criteriaBuilder);
		
	}


	public String getSearch() {
		return search;
	}


	public void setSearch(String search) {
		this.search = search;
	}
	

	
	

}
