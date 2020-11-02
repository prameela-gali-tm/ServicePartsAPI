package com.toyota.scs.serviceparts.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.toyota.scs.serviceparts.model.AsnTPNAModel;
import com.toyota.scs.serviceparts.model.PolineModel;
import com.toyota.scs.serviceparts.service.TpnaService;
import com.toyota.scs.serviceparts.serviceImpl.KafKaProducerService;

@RestController
public class KafkaProducerController {

	@Autowired
	KafKaProducerService kafkaProd;
	@Autowired
	TpnaService tpnaService;
	
	@PostMapping("/kafkaexample")
	public void kafakPush(@RequestBody List<AsnTPNAModel> modList) {
		
		kafkaProd.sendToKafka(modList);
		
	}
	@PostMapping("/tpnainsert")
	public void tpnaPush(@RequestBody List<PolineModel> polineList) {
		
		tpnaService.poDetails(polineList);
		
	}
}
