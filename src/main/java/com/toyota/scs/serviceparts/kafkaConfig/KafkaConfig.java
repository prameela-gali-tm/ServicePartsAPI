package com.toyota.scs.serviceparts.kafkaConfig;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

@EnableKafka
@Configuration
public class KafkaConfig {

	@Bean
	public ConsumerFactory<String, String> consumerFactory() {
		final String BOOTSTRAP_SERVERS = "broker01-int.qa.awskafka.toyota.com:9094,"
				+ "broker02-int.qa.awskafka.toyota.com:9094," + "broker03-int.qa.awskafka.toyota.com:9094,"
				+ "broker04-int.qa.awskafka.toyota.com:9094," + "broker05-int.qa.awskafka.toyota.com:9094";
		// HashMap to store the configurations
		Map<String, Object> map = new HashMap<>();

		// put the host IP inn the map
		map.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);

		// put the group ID in the map
		map.put(ConsumerConfig.GROUP_ID_CONFIG, "id");
		map.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		map.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);

		
		
		  map.put("security.protocol", "SSL"); 
		// map.put("ssl.enabled.protocols", "TLSv1.3,TLSv1.2,TLSv1.1,TLSv1"); 
		
		//  map.put("ssl.enabled.protocols", "TLSv1.3,SSLv3");
		  map.put("ssl.enabled.protocol", "TLSv1.2");
		 
			map.put(
			  "ssl.truststore.location","C:\\Users\\alingannagari\\Desktop\\Project\\Service parts\\Kafka\\kafkaTruststore.jks"
			  ); 
		  map.put("ssl.truststore.password", "changeit");
			 
		  map.put("ssl.key.password", "changeit"); 
		  map.put("ssl.keystore.type", "JKS");
		  map.put("ssl.endpoint.identification.algorithm", "");
		 
		  map.put(ProducerConfig.RETRIES_CONFIG, "1");
		
		
		  map.put("ssl.key.password", "changeit");
		  map.put("ssl.keystore.password","changeit");
		  map.put("ssl.keystore.location",
		  "C:\\Users\\alingannagari\\Desktop\\Project\\Service parts\\Certs\\mykeystore.jks"
		  );
		 
		 
		 
		return new DefaultKafkaConsumerFactory<>(map);
	}

	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListner() {
		ConcurrentKafkaListenerContainerFactory<String, String> obj = new ConcurrentKafkaListenerContainerFactory<>();
		obj.setConsumerFactory(consumerFactory());
		return obj;
	}
}
