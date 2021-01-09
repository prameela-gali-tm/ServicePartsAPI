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
import com.toyota.scs.serviceparts.model.ModelApiResponse;
import com.toyota.scs.serviceparts.model.PartDetailsModel;
import com.toyota.scs.serviceparts.model.PartDetailsModelQuery;
import com.toyota.scs.serviceparts.model.PurchaseOrderDetailsModel;
import com.toyota.scs.serviceparts.model.ViewPONumberDetailModel;
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
		partDetails = partdetailsService.findPartDetails(partNumber, vendorCode,directFlag,transportCode,dealerNumber,distFD,null,null,null);
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
			@RequestParam(name="transportCode", required = true, defaultValue = "3") int transportCode,
			@RequestParam(name = "dealerNumber",required = false) String dealerNumber,
			@RequestParam(name = "distFD",required = false) String distFD){
		
		List<ViewPartDetailsModel> partDetails = new ArrayList<ViewPartDetailsModel>();	
		partDetails = partdetailsService.getViewAllPartDetails(vendorCode,directFlag,transportCode,dealerNumber,distFD);
		return new ResponseEntity<>(partDetails,HttpStatus.OK);
		
	}
	
	@GetMapping("/casedetails")
	public ResponseEntity<ModelApiResponse> getCaseDetails(
			@RequestParam(name="caseNumber", required = true) String caseNumber,
			@RequestParam(name="vendorCode", required = false) String vendorCode,
			@RequestParam(name="directFlag", required = false) String directFlag,
			@RequestParam(name="transportCode", required = false, defaultValue = "0") int transportCode){		
		
		ModelApiResponse apiResponse = new ModelApiResponse();
		apiResponse = partdetailsService.getCaseDetails(caseNumber, vendorCode,directFlag,transportCode);
		return new ResponseEntity<>(apiResponse,HttpStatus.OK);
		
	}
	
	@GetMapping("/viewAllPONumbers")
	public ResponseEntity<List<ViewPONumberDetailModel>> getViewAllPONumbers(
			@RequestParam(name="startDate", required = true) String startDate,
			@RequestParam(name="endDate", required = true) String endDate,
			@RequestParam(name="vendorCode", required = true) String vendorCode,
			@RequestParam(name="status", required = false) String status
			){
		List<ViewPONumberDetailModel> viewPoNumberDetail = new ArrayList<ViewPONumberDetailModel>();	
		viewPoNumberDetail = partdetailsService.getViewAllPONumberDetail(startDate, endDate, vendorCode, status);
		return new ResponseEntity<>(viewPoNumberDetail,HttpStatus.OK);
	}
	
	@GetMapping("/querypartdetails")
	public ResponseEntity<List<PartDetailsModelQuery>> getQueryResult(
			@RequestParam(name="partNumber", required = false) String partNumber,
			@RequestParam(name="vendorCode", required = false) String vendorCode,
			@RequestParam(name="directFlag", required = false, defaultValue = "N") String directFlag,
			@RequestParam(name="transportCode", required = false, defaultValue = "3") int transportCode,
			@RequestParam(name = "dealerNumber",required = false) String dealerNumber,
			@RequestParam(name = "distFD",required = false) String distFD,
			@RequestParam(name = "deliveruDueDate",required = false) String deliveruDueDate,
			@RequestParam(name = "poLineNuber",required = false) String poLineNuber,
			@RequestParam(name = "poNumber",required = false) String poNumber
			){
			List<PartDetailsModelQuery> queryPartDetails = new ArrayList<PartDetailsModelQuery>();	
			queryPartDetails = partdetailsService.findPartDetailsQuery(partNumber,vendorCode,directFlag,transportCode,dealerNumber,distFD,deliveruDueDate,poLineNuber,poNumber);
			return new ResponseEntity<>(queryPartDetails,HttpStatus.OK);
	}
} 
