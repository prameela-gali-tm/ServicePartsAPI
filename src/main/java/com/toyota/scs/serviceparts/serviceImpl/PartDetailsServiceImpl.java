package com.toyota.scs.serviceparts.serviceImpl;

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
	

	@Override
	public List<PartDetailsModel> findPartDetails(String partNumber,String vendorCode) {
		EntityManager em = emf.createEntityManager();
		
		StringBuilder sqlQuery = new StringBuilder();
		sqlQuery.append("Select p,ord from PartEntity p inner join OrderEntity ord on ord.orderId=p.orderId where p.partNumber=?1 and ord.vendorCode=?2 order by p.deliveryDueDate asc");
		Query query = em.createQuery(sqlQuery.toString())
				   .setParameter(1, partNumber)
				   .setParameter(2, vendorCode);		  
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
				   detailsModel.setDeliveryDueDate(partEntity.getDeliveryDueDate());
				   detailsModel.setOrderQuantity(partEntity.getOrderQuantity());
				   detailsModel.setOutstandingQuantity(partEntity.getOutstandingQuantity());
				   detailsModel.setPoNumber(orderEntity.getPoNumber());
				   detailsModel.setOrderType(orderEntity.getOrderType());
				   detailsModel.setVendorCode(orderEntity.getVendorCode());
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
