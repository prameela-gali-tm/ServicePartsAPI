package com.toyota.scs.serviceparts.service;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Component;

import com.toyota.scs.serviceparts.entity.OrderEntity;
import com.toyota.scs.serviceparts.entity.PartEntity;
import com.toyota.scs.serviceparts.model.CaseBuildModel;
import com.toyota.scs.serviceparts.model.ModelApiResponse;

@Component
public interface CasesDetailService {

	public ModelApiResponse casesDetailsValidation(List<CaseBuildModel> caseModel,String status);
	public List<PartEntity> findPoNumberListItemDDD(String poNumber,String lineItem,Date deliverDuoDate,long orderid);
	public int caseNumberDaysValidation(String caseNumber);
	public ModelApiResponse casesBuildVendorAndPartNumber(List<CaseBuildModel> caseModel,String status);
}
