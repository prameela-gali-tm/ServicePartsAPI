package com.toyota.scs.serviceparts.serviceImpl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.toyota.scs.serviceparts.model.PartDetailsModel;
import com.toyota.scs.serviceparts.service.PartDetailsService;
@Service
public class PartDetailsServiceImpl implements PartDetailsService {

	@Autowired
    EntityManagerFactory emf;
	@Override
	public List<PartDetailsModel> findPartDetails(String partNumber,String vendorCode) {
		EntityManager em = emf.createEntityManager();
		
		StringBuilder sqlQuery = new StringBuilder();
		//sqlQuery.append("Select p.partId as partId,p.partNumber as partNumber,p.lineItemNumber,p.DDD,p.partDesc,p.orderQuantity,p.vendorPartNumber,p.directShip,p.homePosition from PartEntity p inner join OrderEntity ord on ord.orderId=p.orderId");
		sqlQuery.append("Select p,ord from PartEntity p inner join OrderEntity ord on ord.orderId=p.orderId");
//		TypedQuery<PartDetailsModel> query = (TypedQuery<PartDetailsModel>) em.createQuery(sqlQuery.toString(),PartDetailsModel.class).getResultList();
		
		   Query query = em.createQuery(sqlQuery.toString(),PartDetailsModel.class);
		  
		  @SuppressWarnings("unchecked") 
		  List<PartDetailsModel> list  =(List<PartDetailsModel>)query.getResultList();
		  em.close();
	      return list;    		     
		     
	}
	
}
