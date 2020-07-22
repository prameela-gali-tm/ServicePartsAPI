package com.toyota.scs.serviceparts.serviceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.toyota.scs.serviceparts.entity.VendorEntity;
import com.toyota.scs.serviceparts.model.CaseBuildModel;
import com.toyota.scs.serviceparts.model.Message;
import com.toyota.scs.serviceparts.model.ModelApiResponse;
import com.toyota.scs.serviceparts.repository.VendorRepositroy;
import com.toyota.scs.serviceparts.service.CasesDetailService;
import com.toyota.scs.serviceparts.util.CommonValidation;
import com.toyota.scs.serviceparts.util.DateUtils;
import com.toyota.scs.serviceparts.util.ServicePartConstant;
@Service("CasesDetailService")
public class CasesDetailServiceImpl implements CasesDetailService {

	static String EMPTY="";
	boolean valid;
	@Autowired
	private VendorRepositroy vendorRepositroy;
	
	@Override
	public ModelApiResponse casesDetailsValidation(List<CaseBuildModel> caseModel, String status) {
		ModelApiResponse message = new ModelApiResponse();
		Message mes = new Message();
		Map<String,Message> mesMap= new HashMap<String,Message>();
		if(caseModel!=null && caseModel.size()>0)
		{	
			for(int i=0;i<caseModel.size();i++) {
				valid=true;
				CaseBuildModel caseBuildModel = caseModel.get(i);
				vendorCodeValiadation(caseBuildModel,mesMap);
				caseBuildModel.getExceptions();
				/*
				 * VendorEntity vendorEntity =
				 * vendorRepositroy.findByVendorCodeEquals(caseBuildModel.getVendorCode());
				 * if(vendorEntity!=null) { validateCaseBulid(caseBuildModel,mesMap); }else {
				 * pushMessage(caseBuildModel.getVendorCode(),ServicePartConstant.
				 * INVALIDVENDORCODE,mesMap); }
				 */
				
			}
		}
		message.setMessages(new ArrayList<Message>(mesMap.values()));
		return message;
	}
	public void vendorCodeValiadation(CaseBuildModel buildModel,Map<String, Message> mesMap) {
		Message mes ;
		String key= "";
		if(!mesMap.containsKey(key)){
			 mes = new Message();			
		}else{
			mes=mesMap.get(key);
		}
		pushMessage(mes, vendorCodeValid(buildModel.getVendorCode()));
	}
	private void pushMessage(String key,String message,Map<String,Message> mesMap){
		if(message.equals(EMPTY)){
			return;
		}
		valid=false;
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
	private void pushMessage(Message mes,String message){
		if(message.equals(EMPTY)){
			return;
		}
		if(mes.getErrorMessages().contains(message)){
			return;
		}		
		mes.getErrorMessages().add(message);
	}
	public String confirmationNumber(String supplier,String type){
		String cm="";
		cm+=supplier;
		cm+=DateUtils.convertfromDateToStringFmt(new Date(), "yyMMddHHmmssss");
		cm+=type;
		return cm;
		
	}
	
	public void validateCaseBulid(CaseBuildModel buildModel,Map<String, Message> mesMap) {
		
		//pushMessage(mes, caseNumberValid(buildModel.getCases().ge));
	}
	
	public String vendorCodeValid(String s){
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
	
	public String caseNumberValid(String s){
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
}
