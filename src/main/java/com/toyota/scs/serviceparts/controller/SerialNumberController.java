package com.toyota.scs.serviceparts.controller;

import java.util.Date;
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
import org.springframework.web.bind.annotation.RestController;

import com.toyota.scs.serviceparts.entity.SerialNumberEntity;
import com.toyota.scs.serviceparts.repository.SerialNumberRepository;
import com.toyota.scs.serviceparts.serviceImpl.SerialNumberService;

@RestController
public class SerialNumberController {

	@Autowired
	SerialNumberRepository serialNumberRepository;
	
	@Autowired
	SerialNumberService service;
	
	@GetMapping("/fetchserialNumber")
	public Page getAllVendor(@RequestParam(defaultValue = "0") Integer pageNo, 
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy){
		Page pageresult = service.getAllSerailNumber(pageNo, pageSize, sortBy);
		 
        return  pageresult; 
	}
	
	@PostMapping("/fetchserialNumber")	// insert into vendor post
	public SerialNumberEntity saveSerialNumberDetails(@RequestBody SerialNumberEntity serialNumberEntity) {
		return serialNumberRepository.save(serialNumberEntity);
	}
	@PutMapping("/fetchserialNumber")//update put
	public String updateSerialNumberDetails(@RequestBody SerialNumberEntity serialNumberEntity) 
	{
		String returnValue="";
		if(serialNumberEntity!=null) {
			Optional<SerialNumberEntity> obj = serialNumberRepository.findById(serialNumberEntity.getId());
			if(obj.isPresent()) {
				SerialNumberEntity obj1 = obj.get();
				if(serialNumberEntity!=null) {
					obj1.setPartTransId(serialNumberEntity.getPartTransId());
					obj1.setSerialNumber(serialNumberEntity.getSerialNumber());
					obj1.setModifiedBy(serialNumberEntity.getModifiedBy());
					obj1.setModifiedDate(new Date());	
					serialNumberRepository.save(obj1);
				}
				returnValue= "Record was updated successfully";
		}
		else {
			returnValue= "Record does not found";
		}
		}
		return returnValue;
	}
	
	@DeleteMapping(value = "/fetchserialNumber/{id}")
	public String deleteSerialNumberDetails(@PathVariable long id) {
		Optional<SerialNumberEntity> obj= serialNumberRepository.findById(id);
		if(obj.isPresent()) {
			serialNumberRepository.deleteById(id);
			 return "Record was deleted successfully for given Id "+ id;
		}else {
			return "Record does not found for given Id  "+ id;
		}
	}
}
