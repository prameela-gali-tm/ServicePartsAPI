package com.toyota.scs.serviceparts.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.toyota.scs.serviceparts.entity.ExceptionEntity;
import com.toyota.scs.serviceparts.repository.ExceptionRepository;
import com.toyota.scs.serviceparts.serviceImpl.ExceptionService;

public class ExceptionController {

	@Autowired
	ExceptionRepository exceptionRepository;
	
	@Autowired
	ExceptionService service;
	
	@GetMapping("/fetchexception")
	public Page getAllVendor(@RequestParam(defaultValue = "0") Integer pageNo, 
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy){
		Page pageresult = service.getAllException(pageNo, pageSize, sortBy);
		 
        return  pageresult; 
	}
	
	@PostMapping("/fetchexception")	// insert into vendor post
	public ExceptionEntity saveExceptionDetails(@RequestBody ExceptionEntity exceptionEntity) {
		return exceptionRepository.save(exceptionEntity);
	}
	
	
	@PutMapping("/fetchexception")//update put
	public String updateVendorDetails(@RequestBody ExceptionEntity exceptionEntity) 
	{
		String returnValue="";
		if(exceptionEntity!=null) {
			Optional<ExceptionEntity> obj = exceptionRepository.findById(exceptionEntity.getId());
			if(obj.isPresent()) {
				ExceptionEntity exceptionEntity1 = obj.get();
				if(exceptionEntity1!=null) {
					exceptionEntity1.setId(exceptionEntity.getId());
					exceptionEntity1.setCaseId(exceptionEntity.getCaseId());
					exceptionEntity1.setComments(exceptionEntity.getComments());
					exceptionEntity1.setExceptionCode(exceptionEntity.getExceptionCode());
					exceptionRepository.save(exceptionEntity1);
				}
				returnValue= "Record was updated successfully";
		}
		}else {
			returnValue= "Record does not found";
		}
		return returnValue;
	}
	
	@DeleteMapping(value = "/fetchexception/{id}")
	public String deleteVendorDetails(@PathVariable long id) {
		Optional<ExceptionEntity> obj= exceptionRepository.findById(id);
		if(obj.isPresent()) {
			exceptionRepository.deleteById(id);
			 return "Record was deleted succefully for given Id "+ id;
		}else {
			return "Record does not found for given Id  "+ id;
		}
	}
}
