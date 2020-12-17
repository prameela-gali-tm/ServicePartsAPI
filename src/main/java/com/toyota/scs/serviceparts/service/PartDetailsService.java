package com.toyota.scs.serviceparts.service;

import java.util.List;

import com.toyota.scs.serviceparts.model.ModelApiResponse;
import com.toyota.scs.serviceparts.model.PartDetailsModel;
import com.toyota.scs.serviceparts.model.PurchaseOrderDetailsModel;
import com.toyota.scs.serviceparts.model.ViewPartDetailsModel;

public interface PartDetailsService {
	
	public List<PartDetailsModel> findPartDetails(String partNumber,String vendorCode,String directFlag, int transportCode,String dealerNumber,String distFD,String deliveruDueDate,String poLineNuber,String poNumber);
	public List<PurchaseOrderDetailsModel> getViewAllPurchaseDetails();
	public List<ViewPartDetailsModel> getViewAllPartDetails(String vendorCode,String directFlag, int transportCode,String dealerNumber,String distFD);
	public ModelApiResponse getCaseDetails(String caseNumber,String vendorCode,String directFlag, int transportCode);
	
}
