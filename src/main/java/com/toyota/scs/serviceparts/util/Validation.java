package com.toyota.scs.serviceparts.util;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import com.toyota.scs.serviceparts.entity.OrderEntity;
import com.toyota.scs.serviceparts.entity.PartEntity;
import com.toyota.scs.serviceparts.entity.VendorEntity;
import com.toyota.scs.serviceparts.model.CaseBuildModel;
import com.toyota.scs.serviceparts.model.Message;
import com.toyota.scs.serviceparts.model.UnitsModel;
import com.toyota.scs.serviceparts.repository.OrderRepositroy;
import com.toyota.scs.serviceparts.repository.PartRepository;
import com.toyota.scs.serviceparts.repository.VendorRepositroy;

public class Validation {
	
	 String EMPTY="";
	 private final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
	 
	 private final SimpleDateFormat DATE_TIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	 
	public  String caseNumberValid(String s){
		String mes=EMPTY;
		if(s==null||s.isEmpty()){
			return ServicePartConstant.VENDORCODE_EMPTY;
		}
		if(s.length()>=12){
			return ServicePartConstant.CASE_NUMBER_LENGTH;
		}
		if(!CommonValidation.isNumeric(s)){
			return ServicePartConstant.CASE_NUMBER_INVALID;
		}		
		return mes;
	}
	
	public  String vendorCodeValid(String s,VendorRepositroy vendorRepositroy){
		String mes=EMPTY;
		if(s==null||s.isEmpty()){
			return ServicePartConstant.VENDORCODE_EMPTY;
		}
		if(!CommonValidation.isAlphaNumeric(s)){
			return ServicePartConstant.INVALIDVENDORCODE;
		}	
		VendorEntity entity = vendorRepositroy.findByVendorCodeEquals(s);
		if(entity==null) {
			return ServicePartConstant.INVALIDVENDORCODE;
		}
		return mes;
	}
	
	public  String confirmationNumber(String vendorCode,String type){
		String cm="";
		cm+=vendorCode.substring(0,4);
		cm+=DateUtils.convertfromDateToStringFmt(new java.util.Date(), "yyMMddHHmmssss");
		cm+=type;
		return cm;
		
	}
	public  void vendorCodeValiadation(CaseBuildModel buildModel,Map<String, Message> mesMap,VendorRepositroy vendorRepositroy) {
		Message mes ;
		String key= "";
		if(!mesMap.containsKey(key)){
			 mes = new Message();			
		}else{
			mes=mesMap.get(key);
		}
		pushMessage(mes, vendorCodeValid(buildModel.getVendorCode(),vendorRepositroy));
	}
	public  OrderEntity vendorPonumberOrderValidation(UnitsModel unitsModel,String vendorCode,Map<String, Message> mesMap,OrderRepositroy orderRepositroy){
		Message mes ;
		String key= vendorCode;
		OrderEntity entity = new OrderEntity();
		if(vendorCode!=null && unitsModel!=null) {
			entity = orderRepositroy.findByPoNumberAndVendorCode(unitsModel.getPoNumber(),vendorCode);
			if(entity==null) {
				if(!mesMap.containsKey(key)){
					 mes = new Message();
					 mes.setKeyObject(key);
				}else{
					mes=mesMap.get(key);
				}
				
				mesMap.put(key, mes);
				pushMessage(mes, ServicePartConstant.ORDER_NOT_FOUND);
			}
		}
		return entity;
	}
	public PartEntity partNumberDDDLineNumbervalidation(UnitsModel obj,OrderEntity entity,Map<String, Message> mesMap,PartRepository partRepositroy,String vendorCode) {
		Message mes ;
		String key= vendorCode+obj.getPartNumber();
		PartEntity partEntity  = new PartEntity();
		if(vendorCode!=null && obj!=null) {
			partEntity = partRepositroy.findByPartNumberAndLineItemNumberAndDeliveryDueDate(obj.getPartNumber(), obj.getPoLineNumber(), parseTimestamp(obj.getDeliveryDueDate()));
			if(!mesMap.containsKey(key)){
				 mes = new Message();
				 mes.setKeyObject(key);
			}else{
				mes=mesMap.get(key);
			}
			if(partEntity!=null) {
				if(partEntity.getOrderId()!=entity.getOrderId()) {
					mesMap.put(key, mes);
					pushMessage(mes, ServicePartConstant.PART_LINE_DDD);
				}
			}else {
				mesMap.put(key, mes);
				pushMessage(mes, ServicePartConstant.PART_LINE_DDD_INVALID);
			}
			
		}
		return partEntity;
		
	}
	private  void pushMessage(String key,String message,Map<String,Message> mesMap){
		if(message.equals(EMPTY)){
			return;
		}
		Message mes ;
	
		if(!mesMap.containsKey(key)){
			 mes = new Message();
			 mes.setKeyObject(key);
		}else{
			mes=mesMap.get(key);
		}
		pushMessage(mes,message);
		mesMap.put(key, mes);
	}
	private  void pushMessage(Message mes,String message){
		if(message.equals(EMPTY)){
			return;
		}
		if(mes.getErrorMessages().contains(message)){
			return;
		}		
		mes.getErrorMessages().add(message);
	}
	
	
	public void validateCaseBulid(CaseBuildModel buildModel,Map<String, Message> mesMap) {
		
		//pushMessage(mes, caseNumberValid(buildModel.getCases().ge));
	}
	
	public java.sql.Date parseDate(String date) {
	    try {
	    	date = date.replace("T", " ");
	        return new Date(DATE_FORMAT.parse(date).getTime());
	    } catch (ParseException e) {
	        throw new IllegalArgumentException(e);
	    }
	}
	 
	public java.sql.Timestamp parseTimestamp(String timestamp) {
	    try {
	    	timestamp = timestamp.replace("T", " ");
	        return new Timestamp(DATE_TIME_FORMAT.parse(timestamp).getTime());
	    } catch (ParseException e) {
	        throw new IllegalArgumentException(e);
	    }
	}
	
}
