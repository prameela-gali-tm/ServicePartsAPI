package com.toyota.scs.serviceparts.service;

import java.util.List;

import com.toyota.scs.serviceparts.model.PartDetailsModel;
import com.toyota.scs.serviceparts.model.PurchaseOrderDetailsModel;

public interface PartDetailsService {
	
	public List<PartDetailsModel> findPartDetails(String partNumber,String vendorCode);
	public List<PurchaseOrderDetailsModel> getViewAllPurchaseDetails();
	
}
