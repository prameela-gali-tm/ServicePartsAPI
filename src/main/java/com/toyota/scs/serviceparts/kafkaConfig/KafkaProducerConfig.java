package com.toyota.scs.serviceparts.kafkaConfig;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

@EnableKafka
@Configuration
public class KafkaProducerConfig {
	final String BOOTSTRAP_SERVERS = "broker01-int.qa.awskafka.toyota.com:9094,"
			+ "broker02-int.qa.awskafka.toyota.com:9094," + "broker03-int.qa.awskafka.toyota.com:9094,"
			+ "broker04-int.qa.awskafka.toyota.com:9094," + "broker05-int.qa.awskafka.toyota.com:9094";
	@Bean
	public ProducerFactory<String, Object> producerFactory(){
		Map<String, Object> map = new HashMap<>();

		// put the host IP inn the map
		map.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
		map.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		map.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);


		  map.put("security.protocol", "SSL"); 
		// map.put("ssl.enabled.protocols", "TLSv1.3,TLSv1.2,TLSv1.1,TLSv1"); 
		
		//  map.put("ssl.enabled.protocols", "TLSv1.3,SSLv3");
		//  map.put("ssl.enabled.protocol", "TLSv1.2");
		  if (System.getProperty("os.name").toLowerCase().indexOf("win") >= 0) {
			  map.put("ssl.truststore.location","src/main/resources/certs/kafkaTruststore.jks");
			  map.put("ssl.keystore.location", "src/main/resources/certs/mykeystore.jks" );
			
		  }else {
			  map.put("ssl.truststore.location","/certs/kafkaTruststore.jks");
			  map.put("ssl.keystore.location", "/certs/mykeystore.jks" );
		  }
		
		  map.put("ssl.truststore.password", "changeit");
			 
		  map.put("ssl.key.password", "changeit"); 
		  map.put("ssl.keystore.type", "JKS");
		  map.put("ssl.endpoint.identification.algorithm", "");
		 
		  map.put(ProducerConfig.RETRIES_CONFIG, "1");
		
		
		  map.put("ssl.key.password", "changeit");
		  map.put("ssl.keystore.password","changeit");
		
		return new DefaultKafkaProducerFactory<String, Object>(map);
		
	}
	@Bean
    public KafkaTemplate<String, Object> kafkaTemplate() {
        return new KafkaTemplate<String, Object>(producerFactory());
    }
}