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
		if(caseModel!=null && caseModel.size()>0)
		{	
			String vendorCode=null;
			Map<Long, Long> orderAndPartId= new HashMap<Long, Long>();
			Map<String, String> vendorAndCaseNumber= new HashMap<String, String>();
			Map<Long,PartTransEntity> partTransMapList = new HashMap<Long, PartTransEntity>();
			Map<Long,PartEntity> partsMapList = new HashMap<Long, PartEntity>();
			for(int i=0;i<caseModel.size();i++) {
				valid=true;
				CaseBuildModel caseBuildModel = caseModel.get(i);
				vendorCode = caseBuildModel.getVendorCode();
				validation.vendorCodeValiadation(caseBuildModel,mesMap,vendorRepositroy);
				List<ExceptionsModel> exceptions= caseBuildModel.getExceptions();
				List<CaseModel> cases = caseBuildModel.getCases();
				if(cases!=null && cases.size()>0) {
					for(int j=0;j<cases.size();j++) {
						CaseModel model = cases.get(j);
						validation.pushMessage(vendorCode, validation.caseNumberValid(model.getCaseNumber()),mesMap);
						if(!vendorAndCaseNumber.containsKey(vendorCode)) {
							vendorAndCaseNumber.put(vendorCode, model.getCaseNumber());
						}
						List<UnitsModel> units = model.getUnits();
						List<RfidDetailsModel> rfidDetails = model.getRfidDetails();
						for(RfidDetailsModel detailsModel:rfidDetails) {
							validation.pushMessage(vendorCode, validation.rfIdValidation(detailsModel.getRfid()),mesMap);
						}
						
					for(UnitsModel obj:units) {
							validation.pushMessage(vendorCode,validation.partNumberValidation(obj.getPartNumber()),mesMap);//part number
							validation.pushMessage(vendorCode, validation.poNumberValidation(obj.getPoNumber()),mesMap);//po number
							validation.pushMessage(vendorCode, validation.poLineNumberValidation(obj.getPoLineNumber()),mesMap);// po line item number							
							validation.pushMessage(vendorCode, validation.partQuantityValidation(obj.getPartQuantity()),mesMap);// part quantity
							
							validation.pushMessage(vendorCode, validation.homePostionValidation(obj.getHomePosition()),mesMap);// home position
							validation.pushMessage(vendorCode, validation.deliverDueDateValidation(obj.getDeliveryDueDate()),mesMap);//delivery due date
							validation.pushMessage(vendorCode, validation.serialNumberValidation(obj.getSerialNumber()),mesMap);// serail number
							validation.pushMessage(vendorCode, validation.subPartNumberValidation(obj.getSubPartNumber()),mesMap);// sub part number	
							if(mesMap.values().size()==0) {
								OrderEntity entity=validation.vendorPonumberOrderValidation(obj,vendorCode,mesMap,orderRepositroy);
								PartEntity partEntity = new PartEntity();
								if(entity!=null) {
								partEntity= validation.partNumberDDDLineNumbervalidation(obj,entity,mesMap,partRepositroy,vendorCode);
								}
								if(mesMap.values().size()==0) {
									if(!orderAndPartId.containsKey(entity.getOrderId())){
										orderAndPartId.put(entity.getOrderId(), partEntity.getPartId());
									}
									if(!partTransMapList.containsKey(partEntity.getPartId())) {
										 PartTransEntity partTransEntity = new PartTransEntity();
										 partTransEntity.setPartId(partEntity.getPartId());
										 partTransEntity.setSupplierTotal(obj.getPartQuantity());
										 partTransEntity.setTransmussionDate(new Date());
										 partTransEntity.setOrderId(entity.getOrderId());
										 partTransEntity.setStatus("CASE BUILD");
										 partTransEntity.setModifiedBy("sreedhar");
										 partTransEntity.setModifiedDate(new Date());
										 partTransEntity.setCaseNumber(model.getCaseNumber());
										partTransMapList.put(partEntity.getPartId(), partTransEntity);
										partsMapList.put(partEntity.getPartId(), partEntity);
									}
								}
							}
						}
					}
					
				}
			}
			if(mesMap.values().size()==0) {
				String confirmationNumber = validation.confirmationNumber(vendorCode,"C");
				message.setConfirmationNumber(confirmationNumber);
				CaseEntity caseEntity = null;
				List<CaseEntity> saveCaseList = new ArrayList<CaseEntity>();
				for(Map.Entry<String, String> entry : vendorAndCaseNumber.entrySet()) {
					caseEntity = new CaseEntity();
					caseEntity.setConfirmationNumber(confirmationNumber);
					caseEntity.setCaseNumber(entry.getValue());
					caseEntity.setStatus("CASE BUILD");
					caseEntity.setModifiedBy("sreedhar");
					caseEntity.setModifiedDate(new Date());
					saveCaseList.add(caseEntity);
				}
				caseRepositroy.saveAll(saveCaseList);
				Map<String, Long> caseNumberWithcaseID = new HashMap<String, Long>();
				for(CaseEntity entity:saveCaseList) {
					if(!caseNumberWithcaseID.containsKey(entity.getCaseNumber())) {
						caseNumberWithcaseID.put(entity.getCaseNumber(), entity.getCaseId());
					}					
				}
				List<PartTransEntity> savePartTrans = new ArrayList<PartTransEntity>();
				List<PartEntity> updatePart = new ArrayList<PartEntity>();
				for(Map.Entry<Long, PartTransEntity> entry:partTransMapList.entrySet()) {
					PartTransEntity obj = entry.getValue();
					PartEntity partEntity = partsMapList.get(entry.getKey());
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
					obj.setCaseId(caseNumberWithcaseID.get(obj.getCaseNumber()));
					savePartTrans.add(obj);
					updatePart.add(partEntity);
				}
				partTransRepositroy.saveAll(savePartTrans);
				partRepositroy.saveAll(updatePart);
				
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
