package com.toyota.scs.serviceparts.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.toyota.scs.serviceparts.entity.PalletSizeLimitEntity;
import com.toyota.scs.serviceparts.entity.VendorEntity;
import com.toyota.scs.serviceparts.model.CaseBuildModel;
import com.toyota.scs.serviceparts.model.ModelApiResponse;
import com.toyota.scs.serviceparts.repository.PalletSizeLimitRepositroy;
import com.toyota.scs.serviceparts.repository.VendorRepositroy;
import com.toyota.scs.serviceparts.service.CasesDetailService;

@RestController
public class PostApiController {

	@Autowired
	private VendorRepositroy vendorRepositroy;
	
	
	@Autowired
	private CasesDetailService caseDetailSer;
	
	@Autowired
	private PalletSizeLimitRepositroy palletSizeLimitRepositroy;
	
	@PostMapping("/vendors")
	public VendorEntity createVendor(@RequestBody VendorEntity vendor) {
		return vendorRepositroy.save(vendor);
	}
	@PostMapping("/casebuild")
	public ResponseEntity<ModelApiResponse> caseBuild(@RequestBody List<CaseBuildModel> caseModelObject,
			@RequestParam(name="status", required = true) String status){
		ModelApiResponse apiResponse = new ModelApiResponse();
		apiResponse = caseDetailSer.casesDetailsValidation(caseModelObject, status);
		return new ResponseEntity<>(apiResponse,HttpStatus.OK);
		
	}@PostMapping("/casebuildAPI")
	public ResponseEntity<ModelApiResponse> caseBuildVendorAndPartNumber(@RequestBody List<CaseBuildModel> caseModelObject,
			@RequestParam(name="status", required = true) String status){
		ModelApiResponse apiResponse = new ModelApiResponse();
		apiResponse = caseDetailSer.casesBuildVendorAndPartNumber(caseModelObject, status);
		return new ResponseEntity<>(apiResponse,HttpStatus.OK);
	}
	
	@PostMapping("/savepalletsizelimit")
	public List<PalletSizeLimitEntity> createPalletSizeLimit(@RequestBody List<PalletSizeLimitEntity> palletSizeLimitEntity) {
		return  (List<PalletSizeLimitEntity>) palletSizeLimitRepositroy.saveAll(palletSizeLimitEntity);
	}
}
