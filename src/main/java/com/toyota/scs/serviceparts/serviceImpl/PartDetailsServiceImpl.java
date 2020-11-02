package com.toyota.scs.serviceparts.serviceImpl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.toyota.scs.serviceparts.entity.OrderEntity;
import com.toyota.scs.serviceparts.entity.PartEntity;
import com.toyota.scs.serviceparts.model.PartDetailsModel;
import com.toyota.scs.serviceparts.model.PurchaseOrderDetailsModel;
import com.toyota.scs.serviceparts.repository.PartRepository;
import com.toyota.scs.serviceparts.service.PartDetailsService;
@Service
public class PartDetailsServiceImpl implements PartDetailsService {

	@Autowired
    EntityManagerFactory emf;
	
	private final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

	private final SimpleDateFormat DATE_TIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm");

	@SuppressWarnings("unchecked")
	@Override
	public List<PartDetailsModel> findPartDetails(String partNumber,String vendorCode,boolean directFlag,int transportCode) {
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
		sqlQuery.append(" ord.transportation_code as transportationCode, ");
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
		if(directFlag) { 
			sqlQuery.append(" and  ord.direct_ship_flag =true "); 
			}else {
				sqlQuery.append(" and  ord.direct_ship_flag =false "); 
			  }
		if(transportCode!=0) {
			sqlQuery.append(" and  ord.transportation_code=").append(transportCode);
		}
		
		//sqlQuery.append(" and  pt.ddd <= (current_date+3)");
		//sqlQuery.append(" order by pt.ddd asc , ord.order_type, pt.part_number asc");
		//sqlQuery.append(" ) as subquery  order by orderType asc");
		sqlQuery.append(" order by pt.ddd asc");
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
			   if(directFlag) {
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
}
