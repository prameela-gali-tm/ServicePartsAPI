package com.toyota.scs.serviceparts.serviceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.toyota.scs.serviceparts.entity.OrderEntity;
import com.toyota.scs.serviceparts.entity.PartEntity;
import com.toyota.scs.serviceparts.service.PartDetailsService;
@Service
public class PartDetailsServiceImpl implements PartDetailsService {

	@Autowired
    EntityManagerFactory emf;
	@Override
	public List<PartEntity> findPartDetails(String partNumber,String vendorCode) {
		EntityManager em = emf.createEntityManager();
		
		StringBuilder sqlQuery = new StringBuilder();
		sqlQuery.append("Select p,ord from PartEntity p inner join OrderEntity ord on ord.orderId=p.orderId where p.partNumber=?1 and ord.vendorCode=?2 order by p.DDD asc");
		Query query = em.createQuery(sqlQuery.toString())
				   .setParameter(1, partNumber)
				   .setParameter(2, vendorCode);		  
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
