package com.toyota.scs.serviceparts.serviceImpl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.toyota.scs.serviceparts.entity.OrderEntity;
import com.toyota.scs.serviceparts.entity.PartEntity;
import com.toyota.scs.serviceparts.model.PolineModel;
import com.toyota.scs.serviceparts.repository.OrderRepository;
import com.toyota.scs.serviceparts.repository.PartRepository;
import com.toyota.scs.serviceparts.service.TpnaService;
import com.toyota.scs.serviceparts.util.CommonValidation;
import com.toyota.scs.serviceparts.util.DateUtils;
@Service
public class TpnaServiceImpl implements TpnaService{
	
	@Autowired
	OrderRepository ordRepo;
	@Autowired
	PartRepository partRepo;
	
	@Override
	public boolean poDetails(List<PolineModel> polineList) {

		//---------- sp_order
		//vDR_CD vendor code
		//pO_NUM PO number
		//oRD_TYP Order type
		//tRANSP_CD trans code		
		//DISTFD final_destination
		//dIRECT_SHP_FLG direct_ship_flag
		//dLR_CODE dealer_code
		Specification<OrderEntity> ordSpec =null;
		boolean issuesExists=false,orderExists=false,partExists=false;
		String poNumber, orderType,vendorCode,partNumber="",poLine="",ddd="",eda;
		Date dddDate,edaDate;
		for(PolineModel poline :polineList) {
			orderExists=false;
			partExists=false;
			poNumber="";orderType="";vendorCode="";partNumber="";poLine="";ddd="";eda="";
			poNumber=poline.getpO_NUM();
			orderType=poline.getoRD_TYP();
			vendorCode=poline.getvDR_CD();
			partNumber=poline.getpART_NUM();
			poLine=poline.getlINE_ITEM_NUM();
			ddd=poline.getdDD();
			eda=poline.geteDA();
			dddDate= DateUtils.convertfromStringToDateFmt(ddd, "MM/dd/yyyy");
			edaDate= DateUtils.convertfromStringToDateFmt(eda, "MM/dd/yyyy");
			
			//check for required fields
			if(CommonValidation.isNullOrEmpty(poNumber)
			   ||CommonValidation.isNullOrEmpty(orderType)
			   ||CommonValidation.isNullOrEmpty(vendorCode)
					) {
				issuesExists=true;
				continue;
			}
			OrderEntity currentOrd=ordRepo.findByPoNumberAndVendorCodeAndOrderType(poNumber, vendorCode, orderType);
			// insert into order table if not exists
			if(currentOrd==null||currentOrd.getId()==0L) {
				orderExists=false;
				 currentOrd= new OrderEntity();
				 
				 currentOrd.setPoNumber(poNumber);
				 currentOrd.setVendorCode(vendorCode);
				 currentOrd.setOrderType(orderType);
				 if(poline.getdIRECT_SHP_FLG().equalsIgnoreCase("Y")) {
					 currentOrd.setDirectShipFlag(true);
				 }else {
					 currentOrd.setDirectShipFlag(false);
				 }
				 
				 currentOrd.setFinalDestination(poline.getdISTFD());
				 currentOrd.setTransCode(poline.gettRANSP_CD());
				 currentOrd.setDealerOrder(poline.getdLR_ORD_REF_NUM());
				 currentOrd.setModifiedDate(new Date());
				 currentOrd.setModifiedBy("TPNA");
				 ordRepo.save(currentOrd);
			}else {
				orderExists=true;
			}
			
			PartEntity part;
			if(orderExists) {
				 part=	partRepo.findByOrderIdAndPartNumberAndLineItemNumberAndDeliveryDueDate
							(currentOrd.getId(), partNumber,poLine, dddDate);
				 if(part==null||part.getId()==0L) {
					 partExists=false;
				 }else {
					 partExists=true;
				 }
			}
			else {
				// insert into part table 
				partExists=false;				
			}
		   
			if(!partExists) {
				 //-------------- sp_part
				//lINE_ITEM_NUM line_item_number
				//pART_NUM part_number
				//dDD ddd
				//tOYOTA_PART_NUM vendor_part_number
				//oRD_QTY_PER_DDD order_quantity
				//
				part=new PartEntity();
				part.setOrderId(currentOrd.getId());
				part.setLineItemNumber(poLine);
				part.setDeliveryDueDate(dddDate);
				part.setPartNumber(partNumber);
				part.setHomePosition(poline.gethP());
				part.setOrderQuantity(Long.valueOf(poline.getoRD_QTY_PER_DDD()));
				part.setVendorPartNumber(poline.gettOYOTA_PART_NUM());
				part.setPartDesc(poline.getpART_DESC());
				part.setFinalDestination(poline.getfINAL_DST());
				part.setEda(edaDate);
				part.setDealerCode(poline.getdLR_CODE());
				part.setModifiedDate(new Date());
				part.setHomePosition(poline.gethP());
				part.setModifiedBy("TPNA");
				part.setStatus("INSERT");
				partRepo.save(part);
			}
		}
		
		return false;
	}

}
