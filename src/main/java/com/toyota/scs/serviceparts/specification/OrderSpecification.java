package com.toyota.scs.serviceparts.specification;

import javax.persistence.criteria.Predicate;

import org.springframework.data.jpa.domain.Specification;

import com.toyota.scs.serviceparts.entity.OrderEntity;

public class OrderSpecification  {

	//po_number, order_type, vendor_code
	public static Specification<OrderEntity> poNumberTypeVendor(String poNumber,String orderType,String vendorCode) {
		
		return (root, query, cb) -> {			
				final Predicate poNumberPr= cb.equal(root.get("poNumber"), poNumber);
				final Predicate orderTypePr= cb.equal(root.get("orderType"), orderType);
				final Predicate vendorCodePr= cb.equal(root.get("vendorCode"), vendorCode);				
				return cb.and(poNumberPr,orderTypePr,vendorCodePr);
	        };
	    }
	

}
