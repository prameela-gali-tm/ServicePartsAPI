package com.toyota.scs.serviceparts.kafkaConfig;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import com.fasterxml.jackson.core.type.TypeReference;
import com.toyota.scs.serviceparts.model.PolineModel;

@EnableKafka
@Configuration
public class KafkaConfig {
	final String BOOTSTRAP_SERVERS = "broker01-int.qa.awskafka.toyota.com:9094,"
			+ "broker02-int.qa.awskafka.toyota.com:9094," + "broker03-int.qa.awskafka.toyota.com:9094,"
			+ "broker04-int.qa.awskafka.toyota.com:9094," + "broker05-int.qa.awskafka.toyota.com:9094";
	@Value("classpath:/certs/kafkaTruststore.jks")
	@Autowired
	Resource resourceTrustFile;
	@Value("classpath:/certs/mykeystore.jks")
	@Autowired
	Resource resourceKeyStoreFile;
	@Bean
	public ConsumerFactory<String, Object> consumerFactory() {
		
		// HashMap to store the configurations
		Map<String, Object> map = new HashMap<>();

		// put the host IP inn the map
		map.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
		/*
		 * map.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
		 * map.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
		 */
		// put the group ID in the map
		//map.put(ConsumerConfig.GROUP_ID_CONFIG, "SCS_Group_id_LOCAL_34");
		
		map.put(ConsumerConfig.GROUP_ID_CONFIG, UUID.randomUUID().toString());
		map.put(ConsumerConfig.CLIENT_ID_CONFIG, "your_client_id");
		map.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
		map.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		map.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, PoDeserializer.class);

	
		
		
		  map.put("security.protocol", "SSL"); 
		// map.put("ssl.enabled.protocols", "TLSv1.3,TLSv1.2,TLSv1.1,TLSv1"); 
		
		//  map.put("ssl.enabled.protocols", "TLSv1.3,SSLv3");
		//  map.put("ssl.enabled.protocol", "TLSv1.2");
		// map.put("ssl.truststore.location","src/main/resources/certs/kafkaTruststore.jks");
		 map.put("ssl.truststore.location","/certs/kafkaTruststore.jks");
		/*
		 * try {
		 * map.put("ssl.truststore.location",resourceTrustFile.getFile().getAbsolutePath
		 * ()); } catch (IOException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); }
		 */
		 		
		 
		  map.put("ssl.truststore.password", "changeit");
			 
		  map.put("ssl.key.password", "changeit"); 
		  map.put("ssl.keystore.type", "JKS");
		  map.put("ssl.endpoint.identification.algorithm", "");
		 
		  map.put(ProducerConfig.RETRIES_CONFIG, "1");
		
		
		  map.put("ssl.key.password", "changeit");
		  map.put("ssl.keystore.password","changeit");
			
		//  map.put("ssl.keystore.location", "src/main/resources/certs/mykeystore.jks" );
		 map.put("ssl.keystore.location", "/certs/mykeystore.jks" );
		 
		 
		 
		 
		return new DefaultKafkaConsumerFactory<>(map);
	}

	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, List<PolineModel>> kafkaListner() {
		ConcurrentKafkaListenerContainerFactory<String, List<PolineModel>> obj = new ConcurrentKafkaListenerContainerFactory<>();
		obj.setConsumerFactory(consumerFactory());
		return obj;
	}
	
	static class CustomDeserializer extends JsonDeserializer<List<PolineModel>> {

        @Override
        public List<PolineModel> deserialize(String topic, Headers headers, byte[] data) {
            return deserialize(topic, data);
        }

        @Override
        public List<PolineModel> deserialize(String topic, byte[] data) {
            if (data == null) {
                return null;
            }
            try {
                return objectMapper.readValue(data, new TypeReference<List<PolineModel>>() {
                });
            } catch (IOException e) {
                throw new SerializationException("Can't deserialize data [" + Arrays.toString(data) +
                        "] from topic [" + topic + "]", e);
            }
        }
    }
	
	
}