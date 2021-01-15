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

import com.toyota.scs.serviceparts.entity.VendorEntity;
import com.toyota.scs.serviceparts.repository.VendorRepositroy;
import com.toyota.scs.serviceparts.serviceImpl.VendorService;

@RestController
public class VendorController {

	@Autowired
	private VendorRepositroy vendorRepositroy;
	
	@Autowired
	VendorService service;
	

	@GetMapping(value = "/findbyvendorcode/{vendoreCode}")
	public VendorEntity findByVedoreCode(@PathVariable String vendoreCode) {
		return vendorRepositroy.findByVendorCodeEquals(vendoreCode);
	}

	@GetMapping("/fetchvendor")
	public Page getAllVendor(@RequestParam(defaultValue = "0") Integer pageNo, 
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "id:asc") String sortBy,
            @RequestParam(defaultValue = "") String search){
		Page pageresult = service.getAllVendor(pageNo, pageSize, sortBy,search);
		 
        return  pageresult; 
	}
	
	@PostMapping("/fetchvendor")	// insert into vendor post
	public VendorEntity saveVendorDetails(@RequestBody VendorEntity vendorEntity) {
		return vendorRepositroy.save(vendorEntity);
	}
	@PutMapping("/fetchvendor")//update put
	public String updateVendorDetails(@RequestBody VendorEntity vendorEntity) 
	{
		String returnValue="";
		if(vendorEntity!=null) {
			Optional<VendorEntity> obj = vendorRepositroy.findById(vendorEntity.getId());
			if(obj.isPresent()) {
				VendorEntity vendorEntity1 = obj.get();
				if(vendorEntity!=null) {
					vendorEntity1.setVendorCode(vendorEntity.getVendorCode());
					vendorEntity1.setVendorDesc(vendorEntity.getVendorDesc());
					vendorEntity1.setTradingPartnerId(vendorEntity.getTradingPartnerId());
					vendorEntity1.setModifiedBy(vendorEntity.getModifiedBy());
					vendorEntity1.setModifiedDate(new Date());	
					vendorRepositroy.save(vendorEntity1);
				}
				returnValue= "Record was updated successfully";
		}
		else {
			returnValue= "Record does not found";
		}
		}
		return returnValue;
	}
	
	@DeleteMapping(value = "/fetchvendor/{id}")
	public String deleteVendorDetails(@PathVariable long id) {
		Optional<VendorEntity> obj= vendorRepositroy.findById(id);
		if(obj.isPresent()) {
			vendorRepositroy.deleteById(id);
			 return "Record was deleted successfully for given Id "+ id;
		}else {
			return "Record does not found for given Id  "+ id;
		}
	}
	
}
