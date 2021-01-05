package com.toyota.scs.serviceparts.kafkaConfig;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.apache.kafka.common.header.Headers;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import com.fasterxml.jackson.core.type.TypeReference;
import com.toyota.scs.serviceparts.model.PolineModel;

public class PoDeserializer  extends JsonDeserializer<List<PolineModel>> {

	 @Override
     public List<PolineModel> deserialize(String topic, Headers headers, byte[] data) {
         return deserialize(topic, data);
     }

     @Override
     public List<PolineModel> deserialize(String topic, byte[] data) {
         if (data == null) {
             return new ArrayList<PolineModel>();
         }
         try {
             return objectMapper.readValue(data, new TypeReference<List<PolineModel>>() {
             });
         } catch (IOException e) {
        	 String s = new String(data, StandardCharsets.UTF_8);
				  System.out.println("Can't deserialize data [" +
				  s + "] from topic [" + topic + "]");
				 
         }
		return new ArrayList<PolineModel>();
     }
	
	}

