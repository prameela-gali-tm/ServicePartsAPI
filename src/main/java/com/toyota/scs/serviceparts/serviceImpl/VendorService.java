package com.toyota.scs.serviceparts.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.toyota.scs.serviceparts.entity.VendorEntity;
import com.toyota.scs.serviceparts.repository.VendorRepositroy;
import com.toyota.scs.serviceparts.specification.OrderSpecification;
import com.toyota.scs.serviceparts.specification.VendorSpecification;
import com.toyota.scs.serviceparts.util.SCSUtil;

@Service
public class VendorService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Autowired
	VendorRepositroy vendorRepositroy;

	public Page getAllVendor(Integer pageNo, Integer pageSize, String sortBy, String search) {
        Pageable paging = PageRequest.of(pageNo, pageSize,SCSUtil.sortHelper(sortBy));
		Page<VendorEntity> pagedResult;
		if (search != null && !search.isEmpty()) {
			VendorSpecification ordSpec = new VendorSpecification(search);
			pagedResult = vendorRepositroy.findAll(ordSpec, paging);
		} else {
			pagedResult = vendorRepositroy.findAll(paging);
		}

		return pagedResult;
	}
}
