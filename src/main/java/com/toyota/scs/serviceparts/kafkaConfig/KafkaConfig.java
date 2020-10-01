package com.toyota.scs.serviceparts.kafkaConfig;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
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
		final String BOOTSTRAP_SERVERS = "SSL://broker01-int.qa.awskafka.toyota.com:9094,"
				+ "SSL://broker02-int.qa.awskafka.toyota.com:9094," + "SSL://broker03-int.qa.awskafka.toyota.com:9094,"
				+ "SSL://broker04-int.qa.awskafka.toyota.com:9094," + "SSL://broker05-int.qa.awskafka.toyota.com:9094";
		// HashMap to store the configurations
		Map<String, Object> map = new HashMap<>();

		// put the host IP inn the map
		map.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);

		// put the group ID in the map
		map.put(ConsumerConfig.GROUP_ID_CONFIG, "id");
		map.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		map.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);

		
		  map.put("security.protocol", "SSL"); map.put("ssl.truststore.location",
		  "C:\\Users\\alingannagari\\Desktop\\Project\\Service parts\\Kafka\\kafkaKeystore.jks"
		  ); map.put("ssl.truststore.password", "changeit");
		  
		  
			/*
			 * map.put("ssl.key.password", "changeit");
			 * map.put("ssl.keystore.password","changeit"); map.put("ssl.keystore.location",
			 * "C:\\Users\\skadapa\\Desktop\\ServiceParts\\ServicePartRequirement\\AWS_broker_certs_int"
			 * );
			 */
		 
		return new DefaultKafkaConsumerFactory<>(map);
	}

	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListner() {
		ConcurrentKafkaListenerContainerFactory<String, String> obj = new ConcurrentKafkaListenerContainerFactory<>();
		obj.setConsumerFactory(consumerFactory());
		return obj;
	}
}
