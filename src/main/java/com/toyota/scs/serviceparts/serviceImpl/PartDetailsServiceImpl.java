package com.toyota.scs.serviceparts.serviceImpl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.toyota.scs.serviceparts.entity.CaseEntity;
import com.toyota.scs.serviceparts.entity.PartEntity;
import com.toyota.scs.serviceparts.entity.SerialNumberEntity;
import com.toyota.scs.serviceparts.model.Message;
import com.toyota.scs.serviceparts.model.ModelApiResponse;
import com.toyota.scs.serviceparts.model.PartDetailsModel;
import com.toyota.scs.serviceparts.model.PurchaseOrderDetailsModel;
import com.toyota.scs.serviceparts.model.ResponseCaseBuildModel;
import com.toyota.scs.serviceparts.model.ResponseCaseModel;
import com.toyota.scs.serviceparts.model.ResponseUnitsModel;
import com.toyota.scs.serviceparts.model.ViewPONumberDetailModel;
import com.toyota.scs.serviceparts.model.ViewPartDetailsModel;
import com.toyota.scs.serviceparts.repository.CaseRepositroy;
import com.toyota.scs.serviceparts.repository.SerialNumberRepository;
import com.toyota.scs.serviceparts.service.PartDetailsService;
import com.toyota.scs.serviceparts.util.ServicePartConstant;
@Service
public class PartDetailsServiceImpl implements PartDetailsService {

	@Autowired
    EntityManagerFactory emf;
	
	@Autowired
	private CaseRepositroy caseRepositroy;
	
	@Autowired
	private SerialNumberRepository serialNumberRepository;
	
	private final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

	private final SimpleDateFormat DATE_TIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	
	static String EMPTY = "";
	boolean valid;
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<PartDetailsModel> findPartDetails(String partNumber,String vendorCode,String directFlag,int transportCode,String dealerNumber,String distFD,String deliveruDueDate,String poLineNuber,String poNumber) {
		EntityManager em = emf.createEntityManager();
		StringBuilder sqlQuery = new StringBuilder();
	//	sqlQuery.append(" select * from (");
		sqlQuery.append(" select  ");
		sqlQuery.append(" pt.part_number as partNumber,  ");
		sqlQuery.append(" pt.ddd as deliveryDueDate, ");
		sqlQuery.append(" pt.order_quantity as orderQuantity, ");
		sqlQuery.append(" pt.outstanding_quantity as outstandingQuantity, ");
		sqlQuery.append(" pt.line_item_number as 	lineItemNumber, ");
		sqlQuery.append(" ord.po_number as poNumber, ");
		sqlQuery.append(" ord.order_type as orderType, ");
		sqlQuery.append(" ord.vendor_code as vendorCode, ");
		sqlQuery.append(" pt.home_position as homePosition, ");
		sqlQuery.append(" pt.part_id as id, ");
		sqlQuery.append(" pt.order_id as orderId, ");
		sqlQuery.append(" pt.container_id as containerID, ");
		sqlQuery.append(" ord.direct_ship_flag as directShipFlag, ");
		sqlQuery.append(" pt.order_ref_number  as orderRefNumber, ");
		sqlQuery.append(" pt.part_desc as partDesc, ");
		sqlQuery.append(" pt.serial_number as serialNumber, ");
		sqlQuery.append(" pt.sub_part_number as subPartNumber, ");
		sqlQuery.append(" pt.vendor_part_number as vendorPartNumber, ");
		sqlQuery.append(" pt.status as status, ");
		sqlQuery.append(" ord.dealer_code as dealerCode, ");
		sqlQuery.append(" ord.trans_code as transportationCode, ");
		sqlQuery.append(" ord.final_destination as finalDestination ");
		sqlQuery.append(" from spadm.sp_part pt ");
		sqlQuery.append(" join spadm.sp_order ord  ");
		sqlQuery.append(" on ord.order_id = pt.order_id ");
		sqlQuery.append(" where 1=1 and pt.status not in ('FULL FILLED','DRAFT')");
		if(partNumber!=null) {
			sqlQuery.append(" and  pt.part_number='").append(partNumber).append("'"); 
			 }
		if(vendorCode!=null) {
			sqlQuery.append(" and  ord.vendor_code='").append(vendorCode).append("'"); 
			  }
		if(directFlag!=null && directFlag.equalsIgnoreCase("Y")) { 
			sqlQuery.append(" and  ord.direct_ship_flag =true "); 
			}else {
				sqlQuery.append(" and  ord.direct_ship_flag =false "); 
			  }
		if(transportCode!=0) {
			sqlQuery.append(" and  ord.trans_code='").append(transportCode).append("'");
		}
		if(deliveruDueDate!=null) {
			sqlQuery.append(" and  pt.ddd ='").append(deliveruDueDate).append("'");
		}		
		if(poLineNuber!=null) {
			 sqlQuery.append(" and  pt.line_item_number='").append(poLineNuber).append("'");
		 }
		if(dealerNumber!=null) {
			sqlQuery.append(" and  ord.dealer_code ='").append(dealerNumber).append("'");
		}
		if(distFD!=null) {
			sqlQuery.append(" and  ord.final_destination = '").append(distFD).append("'");
		}
		if(poNumber!=null && !poNumber.isEmpty()) {
			
			sqlQuery.append(" and  ord.po_number = '").append(poNumber).append("'");
		}
		
		sqlQuery.append(" order by pt.ddd ,pt.part_number,ord.order_type asc");
		  List<PartEntity> list  = new ArrayList<PartEntity>();
		  list =  (List<PartEntity>)em.createNativeQuery(sqlQuery.toString(),"viewPurchaseDetails").getResultList();
		  List<PartDetailsModel> partDetilsList = new ArrayList<PartDetailsModel>();
		  em.close();
		  for(PartEntity partEntity : list) {
			  PartDetailsModel detailsModel = new PartDetailsModel();
			  detailsModel.setPartNumber(partEntity.getPartNumber());
			   detailsModel.setDeliveryDueDate(DATE_FORMAT.format(partEntity.getDeliveryDueDate()));
			   detailsModel.setOrderQuantity(partEntity.getOrderQuantity());
			   detailsModel.setOutstandingQuantity(partEntity.getOutstandingQuantity());
			   detailsModel.setLineItemNumber(partEntity.getLineItemNumber());
			   detailsModel.setPoNumber(partEntity.getPoNumber());
			   detailsModel.setOrderType(partEntity.getOrderType());
			   detailsModel.setVendorCode(partEntity.getVendorCode());
			   detailsModel.setHomePosition(partEntity.getHomePosition());
			   detailsModel.setPartId(partEntity.getId());
			   detailsModel.setOrderId(partEntity.getOrderId());
			   detailsModel.setContainerID(partEntity.getContainerID());
			   if(partEntity.isDirectShipFlag()) {
				   detailsModel.setDirectShip("true");
			   }else {
				   detailsModel.setDirectShip("false");
			   }			  
			   detailsModel.setOrderRefNumber(partEntity.getOrderRefNumber());
			   detailsModel.setPartDesc(partEntity.getPartDesc());
			   detailsModel.setSerialNumber(partEntity.getSerialNumber());
			   detailsModel.setSubPartNumber(partEntity.getSubPartNumber());
			   detailsModel.setVendorPartNumber(partEntity.getVendorPartNumber());
			   detailsModel.setSupplierFullFillQuantity(partEntity.getOrderQuantity()-partEntity.getOutstandingQuantity());
			   detailsModel.setPartialStatus(partEntity.getStatus());
			   if(directFlag!=null && directFlag.equalsIgnoreCase("Y")) {
				   detailsModel.setDealerOrDistinationFD(partEntity.getDealerCode());
			   }else {
				   detailsModel.setDealerOrDistinationFD(partEntity.getFinalDestination()); 
			   }
			   detailsModel.setTransportationCode(Integer.valueOf(partEntity.getTransportationCode()));
		   partDetilsList.add(detailsModel);
		  }
	      return partDetilsList;    		     
		     
	}
	@Override
	public List<PurchaseOrderDetailsModel> getViewAllPurchaseDetails() {
		EntityManager em = emf.createEntityManager();		
		StringBuilder sqlQuery = new StringBuilder();
		sqlQuery.append("select new com.toyota.scs.serviceparts.model.PurchaseOrderDetailsModel(ord.poNumber as poNumber,"
				+ "ord.orderType as orderType,pa.deliveryDueDate as deliveryDueDate,'' as finalDestinationName,pa.directShip as directDhipFlag,"
				+ "count(pa.lineItemNumber)) from PartEntity pa ,OrderEntity ord where pa.orderId = ord.id"
				+" group by ord.poNumber,ord.orderType,pa.deliveryDueDate,pa.directShip");
		Query query = em.createQuery(sqlQuery.toString());
		List<PurchaseOrderDetailsModel> viewPurchaseOrderDetails = query.getResultList();
		return viewPurchaseOrderDetails;
	}
	@Override
	public List<ViewPartDetailsModel> getViewAllPartDetails(String vendorCode, String directFlag,int transportCode,String dealerNumber,String distFD) {
		EntityManager em = emf.createEntityManager();
		StringBuilder sqlQuery = new StringBuilder();
	//	sqlQuery.append(" select * from (");
		sqlQuery.append(" select  ");
		sqlQuery.append(" pt.part_number as partNumber,  ");
		sqlQuery.append(" pt.home_position as homePosition, ");
		if(directFlag!=null && directFlag.equalsIgnoreCase("Y")) {
			sqlQuery.append(" ord.dealer_code as finalDesDealerCode ");
		}else {
			sqlQuery.append(" ord.final_destination as finalDesDealerCode ");
		}
		sqlQuery.append(" from spadm.sp_part pt ");
		sqlQuery.append(" join spadm.sp_order ord  ");
		sqlQuery.append(" on ord.order_id = pt.order_id ");
		sqlQuery.append(" where 1=1 and pt.status not in ('FULL FILLED','DRAFT')");
		if(vendorCode!=null) {
			sqlQuery.append(" and  ord.vendor_code='").append(vendorCode).append("'"); 
			  }
		if(directFlag!=null && directFlag.equalsIgnoreCase("Y")) { 
			sqlQuery.append(" and  ord.direct_ship_flag =true "); 
			if(dealerNumber!=null) {
				sqlQuery.append(" and  ord.dealer_code ='").append(dealerNumber).append("'");
			}
			}else {
				sqlQuery.append(" and  ord.direct_ship_flag =false "); 
				if(distFD!=null) {
					sqlQuery.append(" and  ord.final_destination = '").append(distFD).append("'");
				}
			  }
		if(transportCode!=0) {
			sqlQuery.append(" and  ord.trans_code='").append(transportCode).append("'");
		}
		if(directFlag!=null && directFlag.equalsIgnoreCase("Y")) { 
			sqlQuery.append(" GROUP BY pt.part_number,pt.home_position,ord.dealer_code");
		}else
		{
			sqlQuery.append(" GROUP BY pt.part_number,pt.home_position,ord.final_destination");
		}
		sqlQuery.append(" order by pt.part_number asc");
		  List<PartEntity> list  = new ArrayList<PartEntity>();
		  list =  (List<PartEntity>)em.createNativeQuery(sqlQuery.toString(),"viewAllPartDetails").getResultList();
		  List<ViewPartDetailsModel> partDetilsList = new ArrayList<ViewPartDetailsModel>();
		  em.close();
		  for(PartEntity partEntity : list) {
			  ViewPartDetailsModel detailsModel = new ViewPartDetailsModel();
			  detailsModel.setPartNumber(partEntity.getPartNumber());
			   detailsModel.setHomePosition(partEntity.getHomePosition());
			   detailsModel.setDealerOrDistinationFD(partEntity.getFinalDesDealerCode());
			   partDetilsList.add(detailsModel);
		  }
	      return partDetilsList;   
	}
	@SuppressWarnings("unchecked")
	@Override
	public ModelApiResponse getCaseDetails(String caseNumber, String vendorCode, String directFlag,
			int transportCode) {
		EntityManager em = emf.createEntityManager();
		StringBuilder sql = new StringBuilder();
		sql.append(" select ");
		sql.append(" ord.vendor_code as vendorCode, ");  
		sql.append(" pt.PART_NUMBER as partNumber,  "); 
		sql.append(" ord.PO_NUMBER as poNumber, ");  
		sql.append(" pt.LINE_ITEM_NUMBER as lineItemNumber, ");
		sql.append(" pt.HOME_POSITION as homePosition,  ");
		sql.append(" pt.DDD as deliveryDueDate,  ");
		sql.append(" coalesce(pt.ORDER_QUANTITY,0,pt.ORDER_QUANTITY) as orderQuantity,  ");
		sql.append(" coalesce(pt.OUTSTANDING_QUANTITY,0,pt.OUTSTANDING_QUANTITY) as outstandingQuantity, ");
		sql.append(" coalesce(ptrans.FULLFILLED_QUANTITY,0,ptrans.FULLFILLED_QUANTITY) as fullfilledQuantity,  ");
		sql.append(" pt.status as status,  ");
		sql.append(" ptrans.SERIAL_NUMBER as serialNumber,   ");
		sql.append(" coalesce(ptrans.part_trans_id,0,ptrans.part_trans_id) as id,  ");
		sql.append(" ord.dealer_code as dealerCode,   ");
		sql.append(" ord.final_destination as finalDestination,  ");
		sql.append(" ord.direct_ship_flag as directShipFlag,   ");
		sql.append(" ord.trans_code AS transportationCode, ");
		sql.append(" ord.order_type AS orderType,");
		sql.append(" ca.confirmation_number as confirmationNumber  ");
		sql.append(" from spadm.sp_part pt    ");
		sql.append(" left join spadm.sp_part_trans ptrans on   pt.part_id=ptrans.part_id ");
		sql.append(" join spadm.sp_case ca on ptrans.case_id=ca.case_id and pt.order_id = ptrans.order_id  and   ca.case_number='").append(caseNumber).append("'");
		sql.append(" join spadm.sp_order ord on ord.order_id=pt.order_id   ");
		sql.append(" where 1=1    ");
		sql.append(" union");
		sql.append(" select ");
		sql.append(" ord.vendor_code as vendorCode,   ");
		sql.append(" pt.PART_NUMBER as partNumber,   ");
		sql.append(" ord.PO_NUMBER as poNumber,   ");
		sql.append(" pt.LINE_ITEM_NUMBER as lineItemNumber, ");
		sql.append(" pt.HOME_POSITION as homePosition,   ");
		sql.append(" pt.DDD as deliveryDueDate,  ");
		sql.append(" coalesce(pt.ORDER_QUANTITY,0,pt.ORDER_QUANTITY) as orderQuantity,  ");
		sql.append(" coalesce(pt.OUTSTANDING_QUANTITY,0,pt.OUTSTANDING_QUANTITY) as outstandingQuantity, ");
		sql.append(" coalesce(ptrans.FULLFILLED_QUANTITY,0,ptrans.FULLFILLED_QUANTITY) as fullfilledQuantity,  ");
		sql.append(" pt.status as status,  ");
		sql.append(" null as serialNumber,   ");
		sql.append(" 0 as id,  ");
		sql.append(" ord.dealer_code as dealerCode,   ");
		sql.append(" ord.final_destination as finalDestination,  ");
		sql.append(" ord.direct_ship_flag as directShipFlag,   ");
		sql.append(" ord.trans_code AS transportationCode, ");
		sql.append(" ord.order_type AS orderType,");
		sql.append(" null as confirmationNumber  ");
		sql.append(" from spadm.sp_part pt");
		sql.append(" left join spadm.sp_part_trans ptrans on   pt.part_id=ptrans.part_id ");
		sql.append(" left join spadm.sp_case ca on ptrans.case_id=ca.case_id and pt.order_id = ptrans.order_id ");
		sql.append(" join spadm.sp_order ord on ord.order_id=pt.order_id   ");
		sql.append(" where 1=1   ");
		sql.append(" and ca.case_id is null");
		sql.append(" and pt.part_number in ");
		sql.append(" ( select distinct part_number from spadm.sp_part  where part_id in");
		sql.append(" (select distinct part_id from spadm.sp_part_trans ptrans join spadm.sp_case ca on ptrans.case_id=ca.case_id and   ca.case_number='").append(caseNumber).append("'");
		sql.append(" ))");
		sql.append(" order by deliveryDueDate,partNumber,orderType asc");
		
		valid=true;
		CaseEntity caseEntityDB = caseRepositroy.findByCaseNumber(caseNumber);
		ModelApiResponse message = new ModelApiResponse();
		Map<String, Message> mesMap = new HashMap<String, Message>();
		List<PartEntity> list  = new ArrayList<PartEntity>();
		list =  (List<PartEntity>)em.createNativeQuery(sql.toString(),"viewCaseDetails").getResultList();
		  em.close();
		if(caseEntityDB!=null && list!=null && list.size()>0) {
			ResponseCaseBuildModel responseCaseBuildModel = new ResponseCaseBuildModel();
			responseCaseBuildModel.setVendorCode(vendorCode);
			List<ResponseCaseModel> responseCaseModelsList = new ArrayList<ResponseCaseModel>();
			ResponseCaseModel responseCaseModel = new ResponseCaseModel();
			responseCaseModel.setCaseNumber(caseEntityDB.getCaseNumber());
			responseCaseModel.setStatus(caseEntityDB.getStatus());
			List<ResponseUnitsModel> responseUnitsModelsList = new ArrayList<ResponseUnitsModel>();
			Map<Long,Long> partTransId = new HashMap<Long, Long>();
			for(PartEntity partDetailsModel : list) {
				  ResponseUnitsModel responseUnitsModel = new ResponseUnitsModel();
				  	responseCaseBuildModel.setVendorCode(partDetailsModel.getVendorCode());
					responseUnitsModel.setPartNumber(partDetailsModel.getPartNumber());
					responseUnitsModel.setPoNumber(partDetailsModel.getPoNumber());
					responseUnitsModel.setPoLineNumber(partDetailsModel.getLineItemNumber());
					responseUnitsModel.setPoLineHomePosition(partDetailsModel.getHomePosition());
					responseUnitsModel.setPoLineDeliveryDueDate(DATE_FORMAT.format(partDetailsModel.getDeliveryDueDate()));
					responseUnitsModel.setPartPOLineQuantityOrdered((int) partDetailsModel.getOrderQuantity());
					responseUnitsModel.setPartPOLineQuantityRemaining(partDetailsModel.getOutstandingQuantity());
					responseUnitsModel.setPartPOLineQuantityAllocated(partDetailsModel.getFullfilledQuantity());
					responseUnitsModel.setPartPOLineStatus(partDetailsModel.getStatus());
					if(partDetailsModel.isDirectShipFlag()) {
						responseUnitsModel.setDealerOrFinalDist(partDetailsModel.getDealerCode());
						responseCaseModel.setDirectShipFlag("Y");
					}else {
						responseUnitsModel.setDealerOrFinalDist(partDetailsModel.getFinalDestination());
						responseCaseModel.setDirectShipFlag("N");
					}	
					responseCaseModel.setTransportationCode(Integer.parseInt(partDetailsModel.getTransportationCode()));
					/// need to fetch the serial number
					if(!partTransId.containsKey(partDetailsModel.getId())){
						List<SerialNumberEntity> serialNumberList  = new ArrayList<SerialNumberEntity>();
						serialNumberList = serialNumberRepository.findByPartTransId(partDetailsModel.getId());					
						if(serialNumberList!=null && serialNumberList.size()>0) {
							List<String> serialNumber = new ArrayList<String>();
							for(SerialNumberEntity entity:serialNumberList) {
								serialNumber.add(entity.getSerialNumber());
							}
							responseUnitsModel.setSerialNumberDetailsModel(serialNumber);
							
						}	
						partTransId.put(partDetailsModel.getId(), partDetailsModel.getId());
					}
					responseUnitsModelsList.add(responseUnitsModel);
			  }
			  responseCaseModel.setUnits(responseUnitsModelsList);
			  responseCaseModelsList.add(responseCaseModel);
			  responseCaseBuildModel.setCases(responseCaseModelsList);
			  message.setResponseCaseBuildDetails(responseCaseBuildModel);
			  message.setConfirmationNumber(caseEntityDB.getConfirmationNumber());
		}else {
			pushMessage(caseNumber, ServicePartConstant.CASE_NUMBER_DOES_NOT_EXIST+caseNumber, mesMap);
			valid=false;
		}
		
		if (valid) {
			message.setMessages(null);
		} else {
			message.setMessages(new ArrayList<Message>(mesMap.values()));
		}
		return message;
	}
	
	public void pushMessage(String key, String message, Map<String, Message> mesMap) {
		if (message.equals(EMPTY)) {
			return;
		}
		Message mes;
		if (!mesMap.containsKey(key)) {
			mes = new Message();
			mes.setKeyObject(key);
		} else {
			mes = mesMap.get(key);
		}
		pushMessage(mes, message);
		mesMap.put(key, mes);
	}

	public void pushMessage(Message mes, String message) {
		if (message.equals(EMPTY)) {
			return;
		}
		valid = false;
		if (mes.getErrorMessages().contains(message)) {
			return;
		}
		mes.getErrorMessages().add(message);
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<ViewPONumberDetailModel> getViewAllPONumberDetail(String startDate, String endDate, String vendorCode,
			String status) {
		EntityManager em = emf.createEntityManager();
		StringBuilder sqlQuery = new StringBuilder();
		sqlQuery.append(" select   ");
		sqlQuery.append(" pt.PART_NUMBER as partNumber, ");
		sqlQuery.append(" ord.PO_NUMBER as poNumber, ");
		sqlQuery.append(" pt.DDD as deliveryDueDate, ");
		sqlQuery.append(" pt.OUTSTANDING_QUANTITY as outstandingQuantity, ");
		sqlQuery.append(" pt.status as status ");
		sqlQuery.append(" from spadm.sp_part pt ");
		sqlQuery.append(" join spadm.sp_order ord on ord.order_id=pt.order_id ");
		sqlQuery.append(" where 1=1  ");
		if(startDate!=null && endDate!=null) {
			sqlQuery.append(" and ( pt.DDD between ").append("'").append(startDate).append("'").append(" and ").append("'").append(endDate).append("')"); 
			 }
		if(vendorCode!=null) {
			sqlQuery.append(" and  ord.vendor_code='").append(vendorCode).append("'"); 
		 }
		if(status!=null) {
			sqlQuery.append(" and pt.status ='").append(status.toUpperCase()).append("'");
		}else {
			sqlQuery.append(" and pt.status not in ('FULL FILLED','DRAFT')");
		}		
		sqlQuery.append(" order by pt.ddd ,pt.part_number,ord.order_type asc");
		List<PartEntity> list  = new ArrayList<PartEntity>();
		list =  (List<PartEntity>)em.createNativeQuery(sqlQuery.toString(),"viewPONumberDetails").getResultList();
		em.close();
		List<ViewPONumberDetailModel> poNumberDetailModels = new ArrayList<ViewPONumberDetailModel>();
		for(PartEntity partDetailsModel : list) {
			ViewPONumberDetailModel detailModel = new ViewPONumberDetailModel();
			detailModel.setPoNumber(partDetailsModel.getPoNumber());
			detailModel.setPartNumber(partDetailsModel.getPartNumber());
			detailModel.setDeliveryDueDate(DATE_FORMAT.format(partDetailsModel.getDeliveryDueDate()));
			detailModel.setUnFulFilledQuantity(partDetailsModel.getOutstandingQuantity());
			detailModel.setStatus(partDetailsModel.getStatus());
			poNumberDetailModels.add(detailModel);
		 }
		return poNumberDetailModels;
	}

}
