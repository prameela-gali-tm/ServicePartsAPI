package com.toyota.scs.serviceparts.controller;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.toyota.scs.serviceparts.entity.OrderEntity;
import com.toyota.scs.serviceparts.repository.OrderRepositroy;
import com.toyota.scs.serviceparts.serviceImpl.OrderService;

@RestController
public class OrderController {

	@Autowired
	OrderRepositroy orderRepositroy;
	
	@Autowired
	OrderService service;
	
	@GetMapping("/fetchorder")
	public Page getAllOrder(@RequestParam(defaultValue = "0") Integer pageNo, 
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy){
		Page pageresult = service.getAllOrder(pageNo, pageSize, sortBy);
		 
        return  pageresult; 
	}
	
	@PostMapping("/fetchorder")	// insert into vendor post
	public OrderEntity saveOrderDetails(@RequestBody OrderEntity orderEntity) {
		return orderRepositroy.save(orderEntity);
	}
	@PutMapping("/fetchorder")//update put
	public String updateOrderDetails(@RequestBody OrderEntity orderEntity) 
	{
		String returnValue="";
		if(orderEntity!=null) {
			Optional<OrderEntity> obj = orderRepositroy.findById(orderEntity.getId());
			if(obj.isPresent()) {
				OrderEntity orderEntity1 = obj.get();
				orderEntity1.setModifiedBy(orderEntity.getModifiedBy());
				orderEntity1.setModifiedDate(new Date());
				orderEntity1.setOrderType(orderEntity.getOrderType());
				orderEntity1.setPoNumber(orderEntity.getPoNumber());
				orderEntity1.setVendorCode(orderEntity.getVendorCode());
				orderRepositroy.save(orderEntity1);
				returnValue= "Record was updated successfully for the given id "+orderEntity.getId();
			}
		}		
		else
		{
			returnValue= "Record does not found for the given id " + orderEntity.getId();
		}
		return returnValue;
	}
	
	@DeleteMapping(value = "/fetchorder/{id}")
	public String deleteOrderDetails(@PathVariable long id) {
		Optional<OrderEntity> obj = orderRepositroy.findById(id);
		if(obj.isPresent()) {
			orderRepositroy.deleteById(id);
			 return "Record was deleted succefully for given Id "+ id;
		}else {
			return "Record does not found for the given Id "+ id;
		}
	}
}
