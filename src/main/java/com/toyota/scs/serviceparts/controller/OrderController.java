package com.toyota.scs.serviceparts.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.toyota.scs.serviceparts.entity.OrderEntity;
import com.toyota.scs.serviceparts.repository.OrderRepositroy;

@RestController
public class OrderController {

	@Autowired
	private OrderRepositroy orderRepository;
	
	@GetMapping("/orders")
	public Iterable<OrderEntity> retrieveAllStudents() {
		return orderRepository.findAll();
	}
	@PostMapping("/orders")
	public ResponseEntity<Object> createStudent(@RequestBody OrderEntity order) {
		
		OrderEntity savedOrder = orderRepository.save(order);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(order.getOrderId()).toUri();

		return ResponseEntity.created(location).build();

	}
	
}
