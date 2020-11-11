package com.toyota.scs.serviceparts.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.toyota.scs.serviceparts.entity.OrderEntity;
import com.toyota.scs.serviceparts.entity.PalletSizeLimitEntity;
import com.toyota.scs.serviceparts.model.PartDetailsModel;
import com.toyota.scs.serviceparts.model.PurchaseOrderDetailsModel;
import com.toyota.scs.serviceparts.model.ViewPartDetailsModel;
import com.toyota.scs.serviceparts.repository.OrderRepository;
import com.toyota.scs.serviceparts.repository.PalletSizeLimitRepositroy;
import com.toyota.scs.serviceparts.serviceImpl.PartDetailsServiceImpl;

@RestController
public class GetApiController {

	@Autowired
	private PartDetailsServiceImpl partdetailsService;
	
	
	@Autowired
	private OrderRepository orderRepositroy;
	
	@Autowired
	private PalletSizeLimitRepositroy palletSizeLimitEntity;
	
	/*
	 * @Autowired private KafkaTemplate<String, Object> template;
	 */
	
	
	
	private String topic ="SCS";
	
	@GetMapping("/partdetails")
	public ResponseEntity<List<PartDetailsModel>> getPartDetails(
			@RequestParam(name="partNumber", required = true) String partNumber,
			@RequestParam(name="vendorCode", required = true) String vendorCode,
			@RequestParam(name="directFlag", required = true, defaultValue = "N") String directFlag,
			@RequestParam(name="transportCode", required = true, defaultValue = "3") int transportCode,
			@RequestParam(name = "dealerNumber",required = false) String dealerNumber,
			@RequestParam(name = "distFD",required = false) String distFD){
		
		List<PartDetailsModel> partDetails = new ArrayList<PartDetailsModel>();	
		partDetails = partdetailsService.findPartDetails(partNumber, vendorCode,directFlag,transportCode,dealerNumber,distFD,null,null);
		return new ResponseEntity<>(partDetails,HttpStatus.OK);
		
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
	
	/*
	 * @GetMapping("/publish/{name}") public String publishMessage(@PathVariable
	 * String name) { template.send(topic,"Hi "+name+" welcome to SCS "); return
	 * "Data published"; }
	 */
	
	@GetMapping("/viewallpartdetails")
	public ResponseEntity<List<ViewPartDetailsModel>> getAllPartDetails(
			@RequestParam(name="vendorCode", required = true) String vendorCode,
			@RequestParam(name="directFlag", required = true, defaultValue = "N") String directFlag,
			@RequestParam(name="transportCode", required = true, defaultValue = "3") int transportCode){
		
		List<ViewPartDetailsModel> partDetails = new ArrayList<ViewPartDetailsModel>();	
		partDetails = partdetailsService.getViewAllPartDetails(vendorCode,directFlag,transportCode);
		return new ResponseEntity<>(partDetails,HttpStatus.OK);
		
	}
		
} 
