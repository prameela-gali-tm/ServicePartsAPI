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

import com.toyota.scs.serviceparts.entity.CaseEntity;
import com.toyota.scs.serviceparts.repository.CaseRepositroy;
import com.toyota.scs.serviceparts.serviceImpl.CaseService;

@RestController
public class CaseController {
	@Autowired
	CaseRepositroy caseRepositroy;
	
	@Autowired
	CaseService caseService;
	
	@GetMapping("/fetchcase")
	public Page getAllVendor(@RequestParam(defaultValue = "0") Integer pageNo, 
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy){
		Page pageresult = caseService.getAllCase(pageNo, pageSize, sortBy);		 
        return  pageresult; 
	}
	
	@PostMapping("/fetchcase")	// insert into vendor post
	public CaseEntity saveVendorDetails(@RequestBody CaseEntity caseEntity) {
		return caseRepositroy.save(caseEntity);
	}
	
	@PutMapping("/fetchcase")//update put
	public String updateVendorDetails(@RequestBody CaseEntity caseEntity) 
	{
		String returnValue="";
		if(caseEntity!=null) {
			Optional<CaseEntity> obj = caseRepositroy.findById(caseEntity.getId());
			if(obj.isPresent()) {
				CaseEntity caseEntity1 = obj.get();
				if(caseEntity1!=null) {
					caseEntity1.setId(caseEntity.getId());	
					caseEntity1.setShipment(caseEntity.getShipment());
					caseEntity1.setCaseNumber(caseEntity.getCaseNumber());
					caseEntity1.setConfirmationNumber(caseEntity.getConfirmationNumber());
					caseEntity1.setModifiedBy("SYSTEM");
					caseEntity1.setModifiedDate(new Date());
					caseEntity1.setStatus(caseEntity.getStatus());
					caseRepositroy.save(caseEntity1);
				}
				returnValue= "Record was updated successfully";
		}
		}else {
			returnValue= "Record does not found";
		}
		return returnValue;
	}
	
	@DeleteMapping(value = "/fetchcase/{id}")
	public String deleteVendorDetails(@PathVariable long id) {
		Optional<CaseEntity> obj= caseRepositroy.findById(id);
		if(obj.isPresent()) {
			caseRepositroy.deleteById(id);
			 return "Record was deleted successfully for given Id "+ id;
		}else {
			return "Record does not found for given Id "+ id;
		}
	}
}
