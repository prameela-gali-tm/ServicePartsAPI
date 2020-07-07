package com.toyota.scs.serviceparts.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.toyota.scs.serviceparts.entity.Vendor;
import com.toyota.scs.serviceparts.repository.VendorRepositroy;

@RestController
@RequestMapping("/api")
public class VendorController {

	@Autowired
	private VendorRepositroy vendorRepositroy;
	
	@PostMapping("/vendors")
	public Vendor createVendor(@RequestBody Vendor vendor) {
		return vendorRepositroy.save(vendor);
	}
	
}
