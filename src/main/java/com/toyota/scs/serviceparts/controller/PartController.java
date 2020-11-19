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

import com.toyota.scs.serviceparts.entity.PartEntity;
import com.toyota.scs.serviceparts.repository.PartRepository;
import com.toyota.scs.serviceparts.serviceImpl.PartService;

@RestController
public class PartController {

	@Autowired
	PartRepository partRepository;
	
	@Autowired
	PartService service;
	
	@GetMapping("/fetchpart")
	public Page getAllPart(@RequestParam(defaultValue = "0") Integer pageNo, 
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy){
		Page pageresult = service.getAllPart(pageNo, pageSize, sortBy);
		 
        return  pageresult; 
	}
	
	@PostMapping("/fetchpart")	// insert into vendor post
	public PartEntity savePartDetails(@RequestBody PartEntity partEntity) {
		return partRepository.save(partEntity);
	}
	@PutMapping("/fetchpart")//update put
	public String updatePartDetails(@RequestBody PartEntity partEntity) 
	{
		String returnValue="";
		if(partEntity!=null) {
			if(partEntity.getId()!=0) {
				Optional<PartEntity> obj = partRepository.findById(partEntity.getId());
				if(obj.isPresent()) {
					PartEntity partEntity1 = obj.get();
					partEntity1.setId(partEntity.getId());
					partEntity1.setContainerID(partEntity.getContainerID());
					partEntity1.setDeliveryDueDate(partEntity.getDeliveryDueDate());
					partEntity1.setHomePosition(partEntity.getHomePosition());
					partEntity1.setLineItemNumber(partEntity.getLineItemNumber());
					partEntity1.setModifiedBy("SYSTEM");
					partEntity1.setModifiedDate(new Date());
					partEntity1.setOrderId(partEntity.getOrderId());
					partEntity1.setOrderQuantity(partEntity.getOrderQuantity());
					partEntity1.setOrderRefNumber(partEntity.getOrderRefNumber());
					partEntity1.setOutstandingQuantity(partEntity.getOutstandingQuantity());
					partEntity1.setPartDesc(partEntity.getPartDesc());
					partEntity1.setPartNumber(partEntity.getPartNumber());
					partEntity1.setSerialNumber(partEntity.getSerialNumber());
					partEntity1.setStatus("BATCH");
					partEntity1.setSubPartNumber(partEntity.getSubPartNumber());
					partEntity1.setEda(partEntity.getEda());
					partEntity1.setTransmissionDate(partEntity.getTransmissionDate());
					partEntity1.setVendorPartNumber(partEntity.getVendorPartNumber());
					partRepository.save(partEntity1);
					returnValue= "Record was updated successfully for the given id "+partEntity.getOrderId();
				}
				else
				{
					returnValue= "Record does not found for the given id " + partEntity.getOrderId();
				}
			}
		}
		return returnValue;
	}
	
	@DeleteMapping(value = "/fetchpart/{id}")
	public String deletePartDetails(@PathVariable long id) {
		Optional<PartEntity> obj = partRepository.findById(id);
		if(obj.isPresent()) {
			partRepository.deleteById(id);
			 return "Record was deleted successfully for given Id "+ id;
		}else {
			return "Record does not found for the given Id "+ id;
		}
	}
}
