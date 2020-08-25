package com.toyota.scs.serviceparts.serviceImpl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.toyota.scs.serviceparts.entity.CaseEntity;
import com.toyota.scs.serviceparts.entity.OrderEntity;
import com.toyota.scs.serviceparts.entity.PartEntity;
import com.toyota.scs.serviceparts.entity.PartTransEntity;
import com.toyota.scs.serviceparts.entity.VendorEntity;
import com.toyota.scs.serviceparts.model.CaseBuildModel;
import com.toyota.scs.serviceparts.model.CaseModel;
import com.toyota.scs.serviceparts.model.ExceptionsModel;
import com.toyota.scs.serviceparts.model.Message;
import com.toyota.scs.serviceparts.model.ModelApiResponse;
import com.toyota.scs.serviceparts.model.RfidDetailsModel;
import com.toyota.scs.serviceparts.model.UnitsModel;
import com.toyota.scs.serviceparts.repository.CaseRepositroy;
import com.toyota.scs.serviceparts.repository.OrderRepositroy;
import com.toyota.scs.serviceparts.repository.PartRepository;
import com.toyota.scs.serviceparts.repository.PartTransRepositroy;
import com.toyota.scs.serviceparts.repository.VendorRepositroy;
import com.toyota.scs.serviceparts.service.CasesDetailService;
import com.toyota.scs.serviceparts.util.CommonValidation;
import com.toyota.scs.serviceparts.util.DateUtils;
import com.toyota.scs.serviceparts.util.ServicePartConstant;
import com.toyota.scs.serviceparts.util.Validation;
@Service("CasesDetailService")
public class CasesDetailServiceImpl implements CasesDetailService {
	@Autowired
	private VendorRepositroy vendorRepositroy;
	
	@Autowired
	private OrderRepositroy orderRepositroy;
	
	@Autowired
	private PartRepository partRepositroy;
	
	@Autowired
	private CaseRepositroy caseRepositroy;
	
	@Autowired
	private PartTransRepositroy partTransRepositroy;
	
	@Autowired
    EntityManagerFactory emf;
	
	static String EMPTY="";
	boolean valid;
	@Override
	public ModelApiResponse casesDetailsValidation(List<CaseBuildModel> caseModel, String status) {
		ModelApiResponse message = new ModelApiResponse();
		Message mes = new Message();
		Map<String,Message> mesMap= new HashMap<String,Message>();
		List<OrderEntity> orderList = new ArrayList<OrderEntity>();
		Validation validation = new Validation();
		List<PartTransEntity> partTransList = new ArrayList<PartTransEntity>();
		String vendorCode=null;
		List<String> vendorAndCaseNumber = new ArrayList<String>();
		// validation start here
		if(caseModel!=null && caseModel.size()>0)
		{	
			for(int i=0;i<caseModel.size();i++) {
				valid=true;
				CaseBuildModel caseBuildModel = caseModel.get(i);
				vendorCode = caseBuildModel.getVendorCode();
				validation.vendorCodeValiadation(caseBuildModel,mesMap,vendorRepositroy,mes);
				List<ExceptionsModel> exceptions= caseBuildModel.getExceptions();
				List<CaseModel> cases = caseBuildModel.getCases();
				if(cases!=null && cases.size()>0) {
					for(int j=0;j<cases.size();j++) {
						CaseModel model = cases.get(j);
						vendorAndCaseNumber.add(model.getCaseNumber());
						validation.pushMessage(vendorCode, validation.caseNumberValid(model.getCaseNumber()),mesMap);
						String keyVendor= vendorCode+model.getCaseNumber();						
						List<UnitsModel> units = model.getUnits();
						List<RfidDetailsModel> rfidDetails = model.getRfidDetails();
						for(RfidDetailsModel detailsModel:rfidDetails) {
							validation.pushMessage(vendorCode, validation.rfIdValidation(detailsModel.getRfid()),mesMap);
						}
						for(ExceptionsModel exceptionsModel:exceptions) {
							validation.pushMessage(vendorCode, validation.exceptionValidation(exceptionsModel.getExceptionCode()),mesMap);
						}
						Map<String, String> duplicateValidation = new HashMap<String, String>();
					for(UnitsModel obj:units) {
							validation.pushMessage(vendorCode,validation.partNumberValidation(obj.getPartNumber()),mesMap);//part number
							validation.pushMessage(vendorCode, validation.poNumberValidation(obj.getPoNumber()),mesMap);//po number
							validation.pushMessage(vendorCode, validation.poLineNumberValidation(obj.getPoLineNumber()),mesMap);// po line item number							
							validation.pushMessage(vendorCode, validation.partQuantityValidation(obj.getPartQuantity()),mesMap);// part quantity
							
							validation.pushMessage(vendorCode, validation.homePostionValidation(obj.getHomePosition()),mesMap);// home position
							validation.pushMessage(vendorCode, validation.deliverDueDateValidation(obj.getDeliveryDueDate()),mesMap);//delivery due date
							validation.pushMessage(vendorCode, validation.serialNumberValidation(obj.getSerialNumber()),mesMap);// serail number
							validation.pushMessage(vendorCode, validation.subPartNumberValidation(obj.getSubPartNumber()),mesMap);// sub part number	
							OrderEntity entity=validation.vendorPonumberOrderValidation(obj,vendorCode,mesMap,orderRepositroy,mes);
							PartEntity partEntity = validation.partNumberDDDLineNumbervalidation(obj,entity,mesMap,partRepositroy,vendorCode,duplicateValidation,model.getCaseNumber());
							if(mesMap.values().size()==0) {
							PartTransEntity partTransEntity = new PartTransEntity();
							 partTransEntity.setPartId(partEntity.getPartId());
							 partTransEntity.setSupplierTotal(obj.getPartQuantity());
							 partTransEntity.setTransmussionDate(new Date());
							 partTransEntity.setOrderId(entity.getOrderId());
							 partTransEntity.setStatus("CASE BUILD");
							 partTransEntity.setModifiedBy("sreedhar");
							 partTransEntity.setModifiedDate(new Date());   
							 partTransEntity.setCaseNumber(model.getCaseNumber());
							 partTransEntity.setPartNumber(obj.getPartNumber());
							 partTransEntity.setPoLineItemNumber(obj.getPoLineNumber());
							 partTransEntity.setDeliveryDueDate(obj.getDeliveryDueDate());
							 partTransEntity.setPoNumber(obj.getPoNumber());
							 partTransList.add(partTransEntity);
							}
						}
							
						}
					}
					
				}
		}
		// validation ends here
			
			if(mesMap.values().size()==0) {
				String confirmationNumber = validation.confirmationNumber(vendorCode,"C");
				message.setConfirmationNumber(confirmationNumber);
				CaseEntity caseEntity = null;
				List<CaseEntity> saveCaseList = new ArrayList<CaseEntity>();
				for(String caseNumbervalue : vendorAndCaseNumber) {
					caseEntity = new CaseEntity();
					CaseEntity caseEntityDB = caseRepositroy.findByCaseNumber(caseNumbervalue);
					if(caseEntityDB!=null) {
						caseEntity = caseEntityDB;						
					}
					caseEntity.setCaseNumber(caseNumbervalue);
					caseEntity.setConfirmationNumber(confirmationNumber);
					caseEntity.setStatus("CASE BUILD");
					caseEntity.setModifiedBy("sreedhar");
					caseEntity.setModifiedDate(new Date());					
					saveCaseList.add(caseEntity);
				}
				caseRepositroy.saveAll(saveCaseList);
				for(PartTransEntity entry:partTransList) {
						PartTransEntity obj = entry;
						caseEntity = caseRepositroy.findByCaseNumber(obj.getCaseNumber());
						PartTransEntity partTransEntity =null;
						if(caseEntity!=null) {
						 partTransEntity= partTransRepositroy.findByCaseId(caseEntity.getCaseId());
						}
						PartEntity partEntity = partRepositroy.findByPartNumberAndLineItemNumberAndDeliveryDueDate(entry.getPartNumber(), entry.getPoLineItemNumber(), validation.parseDate(entry.getDeliveryDueDate()));
						if(partTransEntity!=null) {
							obj.setPartTransId(partTransEntity.getPartTransId());
							if(partTransEntity.getCaseId()==caseEntity.getCaseId() && 
									partTransEntity.getPartId()==partEntity.getPartId() &&
											partTransEntity.getOrderId()==partEntity.getOrderId()) {
								if(obj.getSupplierTotal()>partTransEntity.getSupplierTotal()) {
									long differenceAmount = obj.getSupplierTotal()- partTransEntity.getSupplierTotal();
									obj.setSupplierTotal(partTransEntity.getSupplierTotal()+differenceAmount);
									partEntity.setOutstandingQuantity(partEntity.getOutstandingQuantity()-differenceAmount);
								}else {
									long differenceAmount = partTransEntity.getSupplierTotal()-obj.getSupplierTotal();
									obj.setSupplierTotal(partTransEntity.getSupplierTotal()-differenceAmount);
									partEntity.setOutstandingQuantity(partEntity.getOutstandingQuantity()+differenceAmount);
								}
							}
						}else {
							long plannedQuantity = partEntity.getOrderQuantity();
							long actaulShippedQuantity = obj.getSupplierTotal();
							long outStandingQuantity = partEntity.getOutstandingQuantity();
							long blanceQuantity =0;
							if(outStandingQuantity==0) {
								blanceQuantity= plannedQuantity-actaulShippedQuantity;
							}else {
								blanceQuantity = outStandingQuantity-actaulShippedQuantity;
							}
							partEntity.setOutstandingQuantity(blanceQuantity);	
							partEntity.setTransmissionDate(new Date());
							partEntity.setModifiedBy("sreedhar");
							partEntity.setModifiedDate(new Date());
							obj.setCaseId(caseEntity.getCaseId());
						}
						partRepositroy.save(partEntity);
						partTransRepositroy.save(obj);
					}
				}
				
			
		
		message.setMessages(new ArrayList<Message>(mesMap.values()));
		return message;
	}
	
	@Override
	public List<PartEntity> findPoNumberListItemDDD(String partNumber, String lineItem, Date deliverDuoDate, long orderid) {
		EntityManager em = emf.createEntityManager();
		StringBuilder sqlQuery = new StringBuilder();
		sqlQuery.append("Select p from PartEntity p where p.partNumber=?1 and p.lineItemNumber=?2 and p.deliveryDueDate=?3 and p.orderId=?4");
		Query query = em.createQuery(sqlQuery.toString())
				   .setParameter(1, partNumber)
				   .setParameter(2, lineItem)
				   .setParameter(3, deliverDuoDate)
				   .setParameter(4, orderid);
		  @SuppressWarnings("unchecked") 
		  List list  = query.getResultList();
		  List<PartEntity> partDetilsList = new ArrayList<PartEntity>();
		  em.close();
		  for(int i=0;i<list.size();i++) {
			  Object[] list2 = (Object[]) ((Object)list.get(i));
			  if(list2!=null && list2.length>0) {
				   PartEntity  partEntity = (PartEntity)list2[0];
				   OrderEntity orderEntity = (OrderEntity)list2[1];
				   partEntity.setVendorCode(orderEntity.getVendorCode());
				   partEntity.setOrderType(orderEntity.getOrderType());
				   partEntity.setPoNumber(orderEntity.getPoNumber());
				   partDetilsList.add(partEntity);
			  }
		  }
	      return partDetilsList;    	
	}
}
