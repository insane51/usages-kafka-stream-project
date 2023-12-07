package com.userData.usageCostLogger;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Collection;
import java.util.Collections;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class UsageCostLoggerApplication {

	public static void main(String[] args) {

		SpringApplication.run(UsageCostLoggerApplication.class, args);

		final String server = "localhost:9092";
		final String consumerTopic = "usage-cost-topic-01";
		final String groupId = "sink-consumer-group-1";

		Properties consumerProps = new Properties();
		consumerProps.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,server);
		consumerProps.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
		consumerProps.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,StringDeserializer.class.getName());
		consumerProps.setProperty(ConsumerConfig.GROUP_ID_CONFIG,groupId);

		KafkaConsumer<String,String> consumer = new KafkaConsumer<>(consumerProps);
		consumer.subscribe(Collections.singleton(consumerTopic));

		try{
			while(true){
				ConsumerRecords<String,String> consumerRecords = consumer.poll(1);
				System.out.println("Logger record count : " +consumerRecords.count());
				for(ConsumerRecord<String,String> consumerRecord : consumerRecords){
					UsageCostLogger.dataLogger(consumerRecord.value());
				}
				try{
					TimeUnit.MILLISECONDS.sleep(10);
				}catch (Exception e){
					throw e;
				}
			}

		}catch(Exception e){
			e.printStackTrace();
		}finally {
			consumer.close();
		}
	}
}
