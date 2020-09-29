package com.toyota.scs.serviceparts.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.toyota.scs.serviceparts.entity.OrderEntity;
import com.toyota.scs.serviceparts.entity.PalletSizeLimitEntity;
import com.toyota.scs.serviceparts.entity.VendorEntity;
import com.toyota.scs.serviceparts.model.PartDetailsModel;
import com.toyota.scs.serviceparts.model.PurchaseOrderDetailsModel;
import com.toyota.scs.serviceparts.repository.OrderRepositroy;
import com.toyota.scs.serviceparts.repository.PalletSizeLimitRepositroy;
import com.toyota.scs.serviceparts.repository.VendorRepositroy;
import com.toyota.scs.serviceparts.serviceImpl.PartDetailsServiceImpl;

@RestController
public class GetApiController {

	@Autowired
	private PartDetailsServiceImpl partdetailsService;
	
	@Autowired
	private VendorRepositroy vendorRepositroy;
	
	@Autowired
	private OrderRepositroy orderRepositroy;
	
	@Autowired
	private PalletSizeLimitRepositroy palletSizeLimitEntity;
	
	@Autowired
	private KafkaTemplate<String, Object> template;
	
	private String topic ="SCS";
	
	@GetMapping("/partdetails")
	public ResponseEntity<List<PartDetailsModel>> getPartDetails(
			@RequestParam(name="partNumber", required = false) String partNumber,
			@RequestParam(name="vendorCode", required = true) String vendorCode){
		
		List<PartDetailsModel> partDetails = new ArrayList<PartDetailsModel>();	
		partDetails = partdetailsService.findPartDetails(partNumber, vendorCode);
		return new ResponseEntity<>(partDetails,HttpStatus.OK);
		
	}
	
	@GetMapping("/findbyvendorcode")
	public VendorEntity findByVedoreCode(@RequestParam(name="vendoreCode", required = true) String vendoreCode) {
		return vendorRepositroy.findByVendorCodeEquals(vendoreCode);
	}
	@GetMapping("/getallponumber")
	public List<OrderEntity> getAllPoNumber(@RequestParam(name="vendoreCode", required = true) String vendorCode) {
		return (List<OrderEntity>) orderRepositroy.findByVendorCode(vendorCode);
	}
	@GetMapping("/getpurchasedetails")
	public List<PurchaseOrderDetailsModel> getViewAllPurchaseDetails(){
		return partdetailsService.getViewAllPurchaseDetails();
	}
	@GetMapping("/getallpalletsizelimit")
	public List<PalletSizeLimitEntity> getAllPalletSizeLimit()
	{		
		return (List<PalletSizeLimitEntity>) palletSizeLimitEntity.findAll();
	}
	
	@GetMapping("/publish/{name}")
	public String publishMessage(@PathVariable String name)
	{
		template.send(topic,"Hi "+name+" welcome to SCS ");
		return "Data published";
	}
}
