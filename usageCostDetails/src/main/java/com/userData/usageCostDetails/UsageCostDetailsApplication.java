package com.userData.usageCostDetails;

import com.google.gson.Gson;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Collections;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class UsageCostDetailsApplication {

	static long idx =0;
	public static void main(String[] args) {

		SpringApplication.run(UsageCostDetailsApplication.class, args);

		final String server = "localhost:9092";

		final String producerTopic = "usage-cost-topic-01";
		Properties producerProps = new Properties();
		producerProps.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,server);
		producerProps.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		producerProps.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,StringSerializer.class.getName());
		KafkaProducer<String,String> producer = new KafkaProducer<>(producerProps);

		final String consumerTopic = "usage-detail-topic-01";
		final String groupId = "processor-consumer-group-1";
		Properties consumerProps = new Properties();
		consumerProps.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,server);
		consumerProps.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
		consumerProps.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,StringDeserializer.class.getName());
		consumerProps.setProperty(ConsumerConfig.GROUP_ID_CONFIG,groupId);

		KafkaConsumer<String,String> consumer = new KafkaConsumer<>(consumerProps);
		consumer.subscribe(Collections.singleton(consumerTopic));

		try{

			while(true){
				//consume data from the topic "usage-detail-topic-01"
				ConsumerRecords<String,String> consumerRecords = consumer.poll(1);
				for(ConsumerRecord<String,String> consumerRecord : consumerRecords){
					String usageDetailsString = consumerRecord.value();
					UsageDetail usageDetail = new Gson().fromJson(usageDetailsString,UsageDetail.class);
					UsageCostDetails usageCostDetails = UsageCostService.costService(usageDetail);

					//Add data to the topic "usage-cost-topic-01"
					String usageCostDetailsProducerRecordString = new Gson().toJson(usageCostDetails);
					ProducerRecord<String,String> producerRecord = new ProducerRecord<>(producerTopic,usageCostDetailsProducerRecordString);
					producer.send(producerRecord);
				}
				try{
					TimeUnit.MILLISECONDS.sleep(10);
				}catch (Exception e){
					System.out.println("######Time Sleep exception");
					throw e;
				}
			}
		}catch (Exception e){
			e.printStackTrace();
		}finally {
			consumer.close();
			producer.flush();
			producer.close();
		}


	}

}
