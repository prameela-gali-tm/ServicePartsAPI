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

import com.toyota.scs.serviceparts.entity.PartTransEntity;
import com.toyota.scs.serviceparts.repository.PartTransRepositroy;
import com.toyota.scs.serviceparts.serviceImpl.PartTransService;

@RestController
public class PartTransController {

	@Autowired
	PartTransRepositroy partTransRepositroy;
	
	@Autowired
	PartTransService service;
	
	@GetMapping("/fetchparttrans")
	public Page getAllVendor(@RequestParam(defaultValue = "0") Integer pageNo, 
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "id:asc") String sortBy,
            @RequestParam(defaultValue = "") String search){
		Page pageresult = service.getAllPartTrans(pageNo, pageSize, sortBy,search);		 
        return  pageresult; 
	}
	
	@PostMapping("/fetchparttrans")	// insert into vendor post
	public PartTransEntity saveVendorDetails(@RequestBody PartTransEntity partTransEntity) {
		return partTransRepositroy.save(partTransEntity);
	}
	
	@PutMapping("/fetchparttrans")//update put
	public String updateVendorDetails(@RequestBody PartTransEntity partTransEntity) 
	{
		String returnValue="";
		if(partTransRepositroy!=null) {
			Optional<PartTransEntity> obj = partTransRepositroy.findById(partTransEntity.getId());
			if(obj.isPresent()) {
				PartTransEntity partTransRepositroy1 = obj.get();
				if(partTransRepositroy1!=null) {
					partTransRepositroy1.setId(partTransEntity.getId());
					partTransRepositroy1.setCaseId(partTransEntity.getCaseId());
					partTransRepositroy1.setFullfilledQuantity(partTransEntity.getFullfilledQuantity());
					partTransRepositroy1.setModifiedBy("SYSTEM");
					partTransRepositroy1.setModifiedDate(new Date());
					partTransRepositroy1.setOrderId(partTransEntity.getOrderId());
					partTransRepositroy1.setPartId(partTransEntity.getPartId());
					partTransRepositroy1.setSerialNumber(partTransEntity.getSerialNumber());
					partTransRepositroy1.setStatus(partTransEntity.getStatus());
					partTransRepositroy1.setSupplierTotal(partTransEntity.getSupplierTotal());
					partTransRepositroy1.setTransmussionDate(new Date());
					partTransRepositroy.save(partTransRepositroy1);
				}
				returnValue= "Record was updated successfully";
		}
		}else {
			returnValue= "Record does not found";
		}
		return returnValue;
	}
	
	@DeleteMapping(value = "/fetchparttrans/{id}")
	public String deleteVendorDetails(@PathVariable long id) {
		Optional<PartTransEntity> obj= partTransRepositroy.findById(id);
		if(obj.isPresent()) {
			partTransRepositroy.deleteById(id);
			 return "Record was deleted successfully for given Id "+ id;
		}else {
			return "Record does not found for given Id "+ id;
		}
	}
}
