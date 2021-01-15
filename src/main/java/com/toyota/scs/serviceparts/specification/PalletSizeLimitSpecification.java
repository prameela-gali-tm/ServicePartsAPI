package com.toyota.scs.serviceparts.specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.toyota.scs.serviceparts.entity.PalletSizeLimitEntity;
import com.toyota.scs.serviceparts.util.SCSUtil;

public class PalletSizeLimitSpecification  implements Specification<PalletSizeLimitEntity>{

	
	private String search;
	
	
	public PalletSizeLimitSpecification(String search) {
		super();
		this.search = search;
	}


	public PalletSizeLimitSpecification() {
		super();
		// TODO Auto-generated constructor stub
	}


	@Override
	public Predicate toPredicate(Root<PalletSizeLimitEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
		
		
			
			 return SCSUtil.toPredicate(this.search, root, query, criteriaBuilder);
		
	}


	public String getSearch() {
		return search;
	}


	public void setSearch(String search) {
		this.search = search;
	}
	

	
	

}


