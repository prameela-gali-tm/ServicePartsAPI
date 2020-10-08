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

import com.toyota.scs.serviceparts.entity.RoutePathNodeEntity;
import com.toyota.scs.serviceparts.entity.VendorEntity;
import com.toyota.scs.serviceparts.repository.RoutePathNodeRepositroy;
import com.toyota.scs.serviceparts.serviceImpl.RoutePathNodeService;

public class RoutePathNodeController {

	@Autowired
	RoutePathNodeRepositroy pathNodeRepositroy;
	
	@Autowired
	RoutePathNodeService service;
	
	@GetMapping("/fetchroutepathnode")
	public Page getAllVendor(@RequestParam(defaultValue = "0") Integer pageNo, 
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy){
		Page pageresult = service.getAllRoutePathNodeDetails(pageNo, pageSize, sortBy);
		 
        return  pageresult; 
	}
	
	@PostMapping("/fetchroutepathnode")	// insert into vendor post
	public RoutePathNodeEntity saveVendorDetails(@RequestBody RoutePathNodeEntity routePathNodeEntity) {
		return pathNodeRepositroy.save(routePathNodeEntity);
	}
	@PutMapping("/fetchroutepathnode")//update put
	public String updateVendorDetails(@RequestBody RoutePathNodeEntity routePathNodeEntity) 
	{
		String returnValue="";
		if(routePathNodeEntity!=null) {
			Optional<RoutePathNodeEntity> obj = pathNodeRepositroy.findById(routePathNodeEntity.getRoutePathNodeId());
			if(obj.isPresent()) {
				RoutePathNodeEntity obj1 = obj.get();
				if(routePathNodeEntity!=null) {
					//need to write the save code
					pathNodeRepositroy.save(obj1);
				}
				returnValue= "Record was updated successfully";
		}
		}else {
			returnValue= "Record does not found";
		}
		return returnValue;
	}
	
	@DeleteMapping(value = "/fetchroutepathnode/{id}")
	public String deleteVendorDetails(@PathVariable long id) {
		Optional<RoutePathNodeEntity> obj= pathNodeRepositroy.findById(id);
		if(obj.isPresent()) {
			pathNodeRepositroy.deleteById(id);
			 return "Record was deleted succefully for given Id "+ id;
		}else {
			return "Record does not found for given Id  "+ id;
		}
	}
}
