package com.toyota.scs.serviceparts.util;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
			return ServicePartConstant.VENDOR_CODE_EMPTY;
		}
		if(s.length()>8){
			return ServicePartConstant.CASE_NUMBER_INVALID;
		}
		if(!CommonValidation.isNumeric(s)){
			return ServicePartConstant.CASE_NUMBER_NUMBERIC;
		}		
		return mes;
	}
	
	public  String partNumberValidation(String s){
		String mes=EMPTY;
		if(s==null||s.isEmpty()){
			return ServicePartConstant.PART_NUMBER_EMPTY;
		}
		if(s.length()>20){
			return ServicePartConstant.PART_NUMBER_INVALID;
		}
		if(!CommonValidation.isAlphaNumeric(s)) {
			return ServicePartConstant.PART_NUMBER_SPEC;
		}
		return mes;
	}
	
	public  String poNumberValidation(String s){
		String mes=EMPTY;
		if(s==null||s.isEmpty()){
			return ServicePartConstant.PO_NUMBER_EMPTY;
		}
		if(s.length()>8){
			return ServicePartConstant.PO_NUMBER_INVALID;
		}
		if(!CommonValidation.isAlphaNumeric(s)) {
			return ServicePartConstant.PO_NUMBER_SPEC;
		}
		return mes;
	}
	
	public  String poLineNumberValidation(String s){
		String mes=EMPTY;
		if(s==null||s.isEmpty()){
			return ServicePartConstant.PO_LINE_NUMBER_EMPTY;
		}
		if(s.length()>5){
			return ServicePartConstant.PO_LINE_NUMBER_INVALID;
		}	
		if(!CommonValidation.isAlphaNumeric(s)) {
			return ServicePartConstant.PO_LINE_NUMBER_SPEC;
		}
		return mes;
	}
	public  String partQuantityValidation(int s){
		String mes=EMPTY;
		String value = String.valueOf(s);
		/*
		 * if(CommonValidation.isNumeric(value)) { return
		 * ServicePartConstant.PART_QUANTITY_INVALID; }
		 */
		if(value.length()>5) {
			return ServicePartConstant.PART_QUANTITY_INVALID;
		}
		if(s==0){
			return ServicePartConstant.PART_QUANTITY_EMPTY;
		}
			
		return mes;
	}
	
	public String homePostionValidation(String s)
	{
		String mes =EMPTY;
		if(s!=null && !s.equalsIgnoreCase("")) {
			if(s.length()>1)
			{
				return ServicePartConstant.HOMEPOSITION_INVALID;
			}
			if(!CommonValidation.isAlphaNumeric(s)) {
				return ServicePartConstant.HOMEPOSTION_SPEC;
			}
		}
		return mes;
	}
	
	public String serialNumberValidation(String s)
	{
		String mes =EMPTY;
		if(s!=null && !s.equalsIgnoreCase("")) {
			if(s.length()>20)
			{
				return ServicePartConstant.SERIAL_NUMBER_INVALID;
			}
			if(!CommonValidation.isAlphaNumeric(s)) {
				return ServicePartConstant.SERIAL_NUMBWE_SPEC;
			}
		}
		return mes;
	}
	
	public String subPartNumberValidation(String s)
	{
		String mes =EMPTY;
		if(s!=null && !s.equalsIgnoreCase("")) {
			if(s.length()>20)
			{
				return ServicePartConstant.SUB_PARTNUMBER_INVALID;
			}
			if(!CommonValidation.isAlphaNumeric(s)) {
				return ServicePartConstant.SUB_PARTNUMBER_SPEC;
			}
		}
		
		return mes;
	}
	
	public String deliverDueDateValidation(String s)
	{
		String mes =EMPTY;
		if (s.trim().equals(""))
		{
		    return mes;
		}else {
			if(s.length()>10) {
				return ServicePartConstant.DELIVERY_DUEDATE_INVALID;
			}
			String dateValue[]= s.split("-");
			if(dateValue[0].length()>4 || dateValue[1].length()>2 || dateValue[2].length()>2) {
				return ServicePartConstant.DELIVERY_DUEDATE_INVALID;
			}
		}
		return mes;
	}
	
	public String rfIdValidation(String s)
	{
		String mes =EMPTY;
		if(s!=null && !s.equalsIgnoreCase("")) {
			if(s.length()>12)
			{
				return ServicePartConstant.RFID_INVALID;
			}
			if(!CommonValidation.isAlphaNumeric(s)) {
				return ServicePartConstant.RFID_SPC;
			}
		}
		return mes;
	}
	public String exceptionValidation(String s)
	{
		String mes =EMPTY;
		if(s!=null && !s.equalsIgnoreCase("")) {
			if(!CommonValidation.isAlphaNumeric(s)) {
				return ServicePartConstant.EXCEPTION_CODE;
			}
		}
		return mes;
	}
	public  String vendorCodeValid(String s,VendorRepositroy vendorRepositroy){
		String mes=EMPTY;
		if(s==null||s.isEmpty()){
			return ServicePartConstant.VENDOR_CODE_EMPTY;
		}
		if(s!=null && s.length()>5) {
			return ServicePartConstant.VENDOR_CODE_INVALID;
		}
		if(!CommonValidation.isAlphaNumeric(s)){
			return ServicePartConstant.VENDOR_CODE_SPECIAL_CHAR;
		}		
		VendorEntity entity = vendorRepositroy.findByVendorCodeEquals(s);
		if(entity==null) {
			return ServicePartConstant.VENDOR_CODE_DOES_NOT_EXIST;
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
	public  void vendorCodeValiadation(CaseBuildModel buildModel,Map<String, Message> mesMap,VendorRepositroy vendorRepositroy,Message mes) {
	//	 ;
		String key=buildModel.getVendorCode();
		if(!mesMap.containsKey(key)){
			 mes = new Message();
			 mes.setKeyObject(key);
		}else{
			mes=mesMap.get(key);
		}
		mesMap.put(key, mes);
		pushMessage(mes, vendorCodeValid(buildModel.getVendorCode(),vendorRepositroy));
	}
	public  OrderEntity vendorPonumberOrderValidation(UnitsModel unitsModel,String vendorCode,Map<String, Message> mesMap,OrderRepositroy orderRepositroy,Message mes){
		//Message mes ;
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
	public PartEntity partNumberDDDLineNumbervalidation(UnitsModel obj,OrderEntity entity,Map<String, Message> mesMap,PartRepository partRepositroy,String vendorCode,Map<String, String> duplicateValidation,
			String caseNumber) {
		Message mes ;
		String key= vendorCode;
		PartEntity partEntity  = new PartEntity();
		String duplicateKey = obj.getPartNumber()+obj.getPoNumber()+obj.getPoLineNumber()+obj.getDeliveryDueDate();
		if(vendorCode!=null && obj!=null) {
			partEntity = partRepositroy.findByPartNumberAndLineItemNumberAndDeliveryDueDate(obj.getPartNumber(), obj.getPoLineNumber(), parseDate(obj.getDeliveryDueDate()));
			if(!mesMap.containsKey(key)){
				 mes = new Message();
				 mes.setKeyObject(key);
			}else{
				mes=mesMap.get(key);
			}
			if(duplicateValidation.containsKey(duplicateKey)) {
				mesMap.put(key, mes);
				pushMessage(mes, ServicePartConstant.DUPLICATE_UNITS+" "+caseNumber);
			}else {
				duplicateValidation.put(duplicateKey, duplicateKey);
			}
			if(partEntity!=null && entity!=null) {
				if(partEntity.getOrderId()!=entity.getOrderId()) {
					mesMap.put(key, mes);
					pushMessage(mes, ServicePartConstant.PART_LINE_DDD);
				}
				if(partEntity.getOutstandingQuantity()==0) {
					if(obj.getPartQuantity()>partEntity.getOrderQuantity()) {
						mesMap.put(key, mes);
						pushMessage(mes, ServicePartConstant.ORDER_QUANTITY+" for the part numner "+obj.getPartNumber()+" and PoNumber "+ obj.getPoNumber()+" and line item number "+ obj.getPoLineNumber());
					}
				}else
				{
					if(obj.getPartQuantity()>partEntity.getOutstandingQuantity()) {
						mesMap.put(key, mes);
						pushMessage(mes, ServicePartConstant.ORDER_QUANTITY+" for the part numner "+obj.getPartNumber()+" and PoNumber "+ obj.getPoNumber()+" and line item number "+ obj.getPoLineNumber());
					}
				}
			}else {
				mesMap.put(key, mes);
				pushMessage(mes, ServicePartConstant.PART_LINE_DDD_INVALID);
			}
			
		}
		return partEntity;
		
	}
	
	
	  public void pushMessage(String key,String message,Map<String,Message>
	  mesMap){ if(message.equals(EMPTY)){ return; } Message mes ;
	  if(!mesMap.containsKey(key)){ mes = new Message(); mes.setKeyObject(key);
	  }else{ mes=mesMap.get(key); } pushMessage(mes,message); mesMap.put(key, mes);
	  }
	 
	public  void pushMessage(Message mes,String message){
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
