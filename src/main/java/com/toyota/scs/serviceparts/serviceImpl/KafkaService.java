
  package com.toyota.scs.serviceparts.serviceImpl;
  
  import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import
  org.springframework.stereotype.Service;

import com.toyota.scs.serviceparts.model.PolineModel;
import com.toyota.scs.serviceparts.service.TpnaService;
  
  @Service 
  public class KafkaService {
	  Logger logger = LoggerFactory.getLogger(KafkaService.class);
	  @Autowired
	  TpnaService serv;
  @KafkaListener(topics =
// "TEST.SCS.ASN.NAPO.TOPIC",containerFactory="kafkaListner") 
 // "TEST.NAPO.PURCHASEORDER.SCS.TOPIC",containerFactory="kafkaListner") 
  "QA.NAPO.PURCHASEORDER.SCS.TOPIC",containerFactory="kafkaListner") 
  public void publish(List<PolineModel> message) {
	  if(message!=null&&!message.isEmpty()) {
		  serv.poDetails(message);
		  System.out.println(
				  "You have a new message: " + message.toString()); 
					  logger.error("You have a new message: " + message.toString());
	  }
	  
	  
  }
  
  }
 