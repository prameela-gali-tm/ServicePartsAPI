package com.toyota.scs.serviceparts.serviceImpl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.toyota.scs.serviceparts.entity.PartEntity;
import com.toyota.scs.serviceparts.model.PartDetailsModel;
import com.toyota.scs.serviceparts.model.PurchaseOrderDetailsModel;
import com.toyota.scs.serviceparts.model.ViewPartDetailsModel;
import com.toyota.scs.serviceparts.service.PartDetailsService;
@Service
public class PartDetailsServiceImpl implements PartDetailsService {

	@Autowired
    EntityManagerFactory emf;
	
	private final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

	private final SimpleDateFormat DATE_TIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm");

	@SuppressWarnings("unchecked")
	@Override
	public List<PartDetailsModel> findPartDetails(String partNumber,String vendorCode,String directFlag,int transportCode,String dealerNumber,String distFD,String deliveruDueDate,String poLineNuber) {
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
		sqlQuery.append(" pt.dealer as dealer, ");
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
		sqlQuery.append(" where 1=1 and pt.status<>'FULL FILLED'");
		if(partNumber!=null) {
			sqlQuery.append(" and  pt.part_number='").append(partNumber).append("'"); 
			 }
		if(vendorCode!=null) {
			sqlQuery.append(" and  ord.vendor_code='").append(vendorCode).append("'"); 
			  }
		if(directFlag!=null && directFlag.equalsIgnoreCase("Y")) { 
			sqlQuery.append(" and  ord.direct_ship_flag =true "); 
			sqlQuery.append(" and  ord.dealer_code ='").append(dealerNumber).append("'");	
			}else {
				sqlQuery.append(" and  ord.direct_ship_flag =false "); 
				sqlQuery.append(" and  ord.final_destination = '").append(distFD).append("'");
			  }
		if(transportCode!=0) {
			sqlQuery.append(" and  ord.trans_code='").append(transportCode).append("'");
		}
		if(deliveruDueDate!=null) {
			sqlQuery.append(" and  pt.ddd ='").append(deliveruDueDate).append("'");
		}
		/*
		 * if(poLineNuber!=null) {
		 * sqlQuery.append(" and  pt.line_item_number='").append(poLineNuber).append("'"
		 * ); }
		 */
		
		//sqlQuery.append(" and  pt.ddd <= (current_date+3)");
		//sqlQuery.append(" order by pt.ddd asc , ord.order_type, pt.part_number asc");
		//sqlQuery.append(" ) as subquery  order by orderType asc");
		sqlQuery.append(" order by pt.ddd ,pt.part_number asc");
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
			   detailsModel.setDealer(partEntity.getDealer());
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
	public List<ViewPartDetailsModel> getViewAllPartDetails(String vendorCode, String directFlag,int transportCode) {
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
		sqlQuery.append(" where 1=1 and pt.status<>'FULL FILLED'");
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
}
