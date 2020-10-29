
  package com.toyota.scs.serviceparts.serviceImpl;
  
  import org.springframework.kafka.annotation.KafkaListener; import
  org.springframework.stereotype.Service;
  
  @Service 
  public class KafkaService {
  
  @KafkaListener(topics =
// "TEST.SCS.ASN.NAPO.TOPIC",containerFactory="kafkaListner") 
  "TEST.NAPO.PURCHASEORDER.SCS.TOPIC",containerFactory="kafkaListner") 
  public void publish(String message) {
	  System.out.println(
  "You have a new message: " + message); }
  
  }
 