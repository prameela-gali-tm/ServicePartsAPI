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

import com.toyota.scs.serviceparts.entity.OrderEntity;
import com.toyota.scs.serviceparts.entity.PartEntity;
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
						List<UnitsModel> units = model.getUnits();
						List<RfidDetailsModel> rfidDetails = model.getRfidDetails();
					for(UnitsModel obj:units) {
							OrderEntity entity=validation.vendorPonumberOrderValidation(obj,vendorCode,mesMap,orderRepositroy);
							PartEntity partEntoty = new PartEntity();
							if(entity!=null) {
							partEntoty= validation.partNumberDDDLineNumbervalidation(obj,entity,mesMap,partRepositroy,vendorCode);
							}
							if(mesMap.values().size()==0) {
								if(!orderAndPartId.containsKey(entity.getOrderId())){
									orderAndPartId.put(entity.getOrderId(), partEntoty.getPartId());
								}
							}
						}
					}
					
				}
			}
			if(mesMap.values().size()==0) {
				String confirmationNumber = validation.confirmationNumber(vendorCode,"C");
				message.setConfirmationNumber(confirmationNumber);
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
