package com.toyota.scs.serviceparts.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.toyota.scs.serviceparts.entity.SerialNumberEntity;
import com.toyota.scs.serviceparts.repository.SerialNumberRepository;
import com.toyota.scs.serviceparts.specification.OrderSpecification;
import com.toyota.scs.serviceparts.specification.SerialNumberSpecification;

@Service
public class SerialNumberService {

	@Autowired
	SerialNumberRepository serialNumberRepository;

	public Page getAllSerailNumber(Integer pageNo, Integer pageSize, String sortBy, String search) {
		Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).ascending());
		Page<SerialNumberEntity> pagedResult;
		if (search != null && !search.isEmpty()) {
			SerialNumberSpecification ordSpec = new SerialNumberSpecification(search);
			pagedResult = serialNumberRepository.findAll(ordSpec, paging);
		} else {
			pagedResult = serialNumberRepository.findAll(paging);
		}

		return pagedResult;
	}
}
