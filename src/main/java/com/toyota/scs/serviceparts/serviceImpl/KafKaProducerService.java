package com.toyota.scs.serviceparts.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.toyota.scs.serviceparts.model.AsnTPNAModel;
@Service
public class KafKaProducerService {

	@Autowired()
	private KafkaTemplate<String, Object> kafkaTemplate;
	
	
	public void sendToKafka(List<AsnTPNAModel> tpnaList) {
		
		this.kafkaTemplate.send("TEST.SCS.ASN.NAPO.TOPIC", tpnaList);
	}
}
