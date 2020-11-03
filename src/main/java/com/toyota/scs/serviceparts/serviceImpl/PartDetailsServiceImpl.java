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

	@Override
	public List<PartDetailsModel> findPartDetails(String partNumber,String vendorCode) {
		EntityManager em = emf.createEntityManager();
		
		StringBuilder sqlQuery = new StringBuilder();
		Query query =null;
		if(partNumber!=null) {
			sqlQuery.append("Select p,ord from PartEntity p inner join OrderEntity ord on ord.id=p.orderId where p.partNumber=?1 and ord.vendorCode=?2 and p.status<>'FULL FILLED' order by p.deliveryDueDate asc,p.orderId asc");
			 query = em.createQuery(sqlQuery.toString())
					   .setParameter(1, partNumber)
					   .setParameter(2, vendorCode);	
		}else {
			sqlQuery.append("Select p,ord from PartEntity p inner join OrderEntity ord on ord.id=p.orderId where ord.vendorCode=?1 and p.status<>'FULL FILLED' order by p.deliveryDueDate asc,p.orderId asc");
			 query = em.createQuery(sqlQuery.toString())
					   .setParameter(1, vendorCode);
		}
		  @SuppressWarnings("unchecked") 
		  List list  = query.getResultList();
		  List<PartDetailsModel> partDetilsList = new ArrayList<PartDetailsModel>();
		  em.close();
		  for(int i=0;i<list.size();i++) {
			  Object[] list2 = (Object[]) ((Object)list.get(i));
			  if(list2!=null && list2.length>0) {
				  PartDetailsModel detailsModel = new PartDetailsModel();
				   PartEntity  partEntity = (PartEntity)list2[0];
				   OrderEntity orderEntity = (OrderEntity)list2[1];
					   detailsModel.setPartNumber(partEntity.getPartNumber());
					   detailsModel.setDeliveryDueDate(DATE_FORMAT.format(partEntity.getDeliveryDueDate()));
					   detailsModel.setOrderQuantity(partEntity.getOrderQuantity());
					   detailsModel.setOutstandingQuantity(partEntity.getOutstandingQuantity());
					   detailsModel.setLineItemNumber(partEntity.getLineItemNumber());
					   detailsModel.setPoNumber(orderEntity.getPoNumber());
					   detailsModel.setOrderType(orderEntity.getOrderType());
					   detailsModel.setVendorCode(orderEntity.getVendorCode());
					   detailsModel.setHomePosition(partEntity.getHomePosition());
					   detailsModel.setPartId(partEntity.getId());
					   detailsModel.setOrderId(orderEntity.getId());
					   detailsModel.setContainerID(partEntity.getContainerID());
					   detailsModel.setDealer(partEntity.getDealer());
					   detailsModel.setDirectShip(partEntity.getDirectShip());
					   detailsModel.setOrderRefNumber(partEntity.getOrderRefNumber());
					   detailsModel.setPartDesc(partEntity.getPartDesc());
					   detailsModel.setSerialNumber(partEntity.getSerialNumber());
					   detailsModel.setSubPartNumber(partEntity.getSubPartNumber());
					   detailsModel.setVendorPartNumber(partEntity.getVendorPartNumber());
					   detailsModel.setSupplierFullFillQuantity(partEntity.getOrderQuantity()-partEntity.getOutstandingQuantity());
					   detailsModel.setPartialStatus(partEntity.getStatus());
				   partDetilsList.add(detailsModel);
			  }
		  }
	      return partDetilsList;    		     
		     
	}
	@Override
	public List<PurchaseOrderDetailsModel> getViewAllPurchaseDetails() {
		EntityManager em = emf.createEntityManager();		
		StringBuilder sqlQuery = new StringBuilder();
		sqlQuery.append("select new com.toyota.scs.serviceparts.model.PurchaseOrderDetailsModel(ord.poNumber as poNumber,"
				+ "ord.orderType as orderType,pa.deliveryDueDate as deliveryDueDate,'' as finalDestinationName,pa.directShip as directDhipFlag,"
				+ "count(pa.lineItemNumber)) from PartEntity pa ,OrderEntity ord where pa.orderId = ord.orderId"
				+" group by ord.poNumber,ord.orderType,pa.deliveryDueDate,pa.directShip");
		Query query = em.createQuery(sqlQuery.toString());
		List<PurchaseOrderDetailsModel> viewPurchaseOrderDetails = query.getResultList();
		return viewPurchaseOrderDetails;
	}
}
