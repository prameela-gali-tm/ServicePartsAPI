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

import com.toyota.scs.serviceparts.entity.RoutePathEntity;
import com.toyota.scs.serviceparts.entity.VendorEntity;
import com.toyota.scs.serviceparts.repository.RoutePathRepositroy;
import com.toyota.scs.serviceparts.serviceImpl.RoutePathService;
@RestController
public class RoutePathController {

	@Autowired
	RoutePathRepositroy routePathRepositroy;
	
	@Autowired
	RoutePathService service;
	
	@GetMapping("/fetchroutepath")
	public Page getAllVendor(@RequestParam(defaultValue = "0") Integer pageNo, 
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy){
		Page pageresult = service.getAllRoutePathDetails(pageNo, pageSize, sortBy);
		 
        return  pageresult; 
	}
	
	@PostMapping("/fetchroutepath")	// insert into vendor post
	public RoutePathEntity saveVendorDetails(@RequestBody RoutePathEntity routePathEntity) {
		return routePathRepositroy.save(routePathEntity);
	}
	@PutMapping("/fetchroutepath")//update put
	public String updateVendorDetails(@RequestBody RoutePathEntity routePathEntity) 
	{
		String returnValue="";
		if(routePathEntity!=null) {
			Optional<RoutePathEntity> obj = routePathRepositroy.findById(routePathEntity.getId());
			if(obj.isPresent()) {
				RoutePathEntity obj1 = obj.get();
				if(routePathEntity!=null) {
					obj1.setModifiedBy("SYSTEM");
					obj1.setModifiedDate(new Date());
					obj1.setRouteSeq(routePathEntity.getRouteSeq());
					routePathRepositroy.save(obj1);
				}
				returnValue= "Record was updated successfully";
		}
		}else {
			returnValue= "Record does not found";
		}
		return returnValue;
	}
	
	@DeleteMapping(value = "/fetchroutepath/{id}")
	public String deleteVendorDetails(@PathVariable long id) {
		Optional<RoutePathEntity> obj= routePathRepositroy.findById(id);
		if(obj.isPresent()) {
			routePathRepositroy.deleteById(id);
			 return "Record was deleted successfully for given Id "+ id;
		}else {
			return "Record does not found for given Id  "+ id;
		}
	}
}
