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

import com.toyota.scs.serviceparts.entity.PalletSizeLimitEntity;
import com.toyota.scs.serviceparts.repository.PalletSizeLimitRepositroy;
import com.toyota.scs.serviceparts.serviceImpl.PalletSizeLimitService;
@RestController
public class PalletSizeLimitController {

	@Autowired
	PalletSizeLimitRepositroy palletSizeLimitRepositroy;
	
	@Autowired
	PalletSizeLimitService service;
	
	@GetMapping("/fetchpalletsizelimit")
	public Page getAllVendor(@RequestParam(defaultValue = "0") Integer pageNo, 
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "id:asc") String sortBy,
            @RequestParam(defaultValue = "") String search){
		Page pageresult = service.getAllPalletSizeLimit(pageNo, pageSize, sortBy,search);
        return  pageresult; 
	}
	
	@PostMapping("/fetchpalletsizelimit")	// insert into vendor post
	public PalletSizeLimitEntity saveVendorDetails(@RequestBody PalletSizeLimitEntity palletSizeLimitEntity) {
		return palletSizeLimitRepositroy.save(palletSizeLimitEntity);
	}
	@PutMapping("/fetchpalletsizelimit")//update put
	public String updateVendorDetails(@RequestBody PalletSizeLimitEntity palletSizeLimitEntity) 
	{
		String returnValue="";
		if(palletSizeLimitEntity!=null) {
			Optional<PalletSizeLimitEntity> obj = palletSizeLimitRepositroy.findById(palletSizeLimitEntity.getId());
			if(obj.isPresent()) {
				PalletSizeLimitEntity obj1 = obj.get();
				if(palletSizeLimitEntity!=null) {
					obj1.setId(palletSizeLimitEntity.getId());
					obj1.setDestinationFd(palletSizeLimitEntity.getDestinationFd());
					obj1.setHeight(palletSizeLimitEntity.getHeight());
					obj1.setHomePosition(palletSizeLimitEntity.getHomePosition());
					obj1.setLength(palletSizeLimitEntity.getLength());
					obj1.setModifiedBy("SYSTEM");
					obj1.setModifiedDate(new Date());
					obj1.setWidth(palletSizeLimitEntity.getWidth());
					palletSizeLimitRepositroy.save(obj1);
				}
				returnValue= "Record was updated successfully";
		}
		}else {
			returnValue= "Record does not found";
		}
		return returnValue;
	}
	
	@DeleteMapping(value = "/fetchpalletsizelimit/{id}")
	public String deleteVendorDetails(@PathVariable long id) {
		Optional<PalletSizeLimitEntity> obj= palletSizeLimitRepositroy.findById(id);
		if(obj.isPresent()) {
			palletSizeLimitRepositroy.deleteById(id);
			 return "Record was deleted successfully for given Id "+ id;
		}else {
			return "Record does not found for given Id  "+ id;
		}
	}
}
