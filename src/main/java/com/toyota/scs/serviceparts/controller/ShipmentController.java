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

import com.akiban.sql.parser.SetRoleNode;
import com.toyota.scs.serviceparts.entity.ShipmentEntity;
import com.toyota.scs.serviceparts.entity.VendorEntity;
import com.toyota.scs.serviceparts.repository.ShipmentRepositroy;
import com.toyota.scs.serviceparts.serviceImpl.ShipmentService;

public class ShipmentController {

	@Autowired
	ShipmentRepositroy shipmentRepositroy;
	
	@Autowired
	ShipmentService service;
	
	@GetMapping("/fetchshipment")
	public Page getAllVendor(@RequestParam(defaultValue = "0") Integer pageNo, 
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy){
		Page pageresult = service.getAllShipmentDetails(pageNo, pageSize, sortBy);
		 
        return  pageresult; 
	}
	
	@PostMapping("/fetchshipment")	// insert into vendor post
	public ShipmentEntity saveVendorDetails(@RequestBody ShipmentEntity shipmentEntity) {
		return shipmentRepositroy.save(shipmentEntity);
	}
	@PutMapping("/fetchshipment")//update put
	public String updateVendorDetails(@RequestBody ShipmentEntity shipmentEntity) 
	{
		String returnValue="";
		if(shipmentEntity!=null) {
			Optional<ShipmentEntity> obj = shipmentRepositroy.findById(shipmentEntity.getId());
			if(obj.isPresent()) {
				ShipmentEntity obj1 = obj.get();
				if(shipmentEntity!=null) {
					// need to write the save path
					obj1.setConfirmationNumber(shipmentEntity.getConfirmationNumber());
					obj1.setDriverFirstName(shipmentEntity.getDriverFirstName());
					obj1.setDriverLastName(shipmentEntity.getDriverLastName());
					obj1.setGeoLattitude(shipmentEntity.getGeoLattitude());
					obj1.setGeoLongitude(shipmentEntity.getGeoLongitude());
					obj1.setModifiedBy("SYSTEM");
					obj1.setModifiedDate(new Date());
					obj1.setRoute(shipmentEntity.getRoute());
					obj1.setRun(shipmentEntity.getRun());
					obj1.setScacCode(shipmentEntity.getScacCode());
					obj1.setShipDate(new Date());
					obj1.setStatus(shipmentEntity.getStatus());
					obj1.setSupplierFirstName(shipmentEntity.getSupplierFirstName());
					obj1.setSupplierLastName(shipmentEntity.getSupplierLastName());
					obj1.setTrackingNumber(shipmentEntity.getTrackingNumber());
					obj1.setTrailer(shipmentEntity.getTrailer());					
					shipmentRepositroy.save(obj1);
				}
				returnValue= "Record was updated successfully";
		}
		}else {
			returnValue= "Record does not found";
		}
		return returnValue;
	}
	
	@DeleteMapping(value = "/fetchshipment/{id}")
	public String deleteVendorDetails(@PathVariable long id) {
		Optional<ShipmentEntity> obj= shipmentRepositroy.findById(id);
		if(obj.isPresent()) {
			shipmentRepositroy.deleteById(id);
			 return "Record was deleted succefully for given Id "+ id;
		}else {
			return "Record does not found for given Id  "+ id;
		}
	}
}
