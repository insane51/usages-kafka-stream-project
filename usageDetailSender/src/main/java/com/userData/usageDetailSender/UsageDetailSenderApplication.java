package com.userData.usageDetailSender;

import com.google.gson.Gson;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Properties;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class UsageDetailSenderApplication {

	public static void main(String[] args) {

		SpringApplication.run(UsageDetailSenderApplication.class, args);

		Properties properties = new Properties();
		final String bootstrapServer = "localhost:9092";
		final String topic = "usage-detail-topic-01";

		properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,bootstrapServer);
		properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

		KafkaProducer<String,String> producer = new KafkaProducer<>(properties);

		for(int i=0;i<Integer.MAX_VALUE;i++){
			//To convert object in Json string
			String usageDetailString = new Gson().toJson(UsageDetailSender.userSupplier());
			ProducerRecord<String,String> record = new ProducerRecord<>(topic,usageDetailString);
//			System.out.println(record);

			try {
				// Sleep for 1 second using TimeUnit
				TimeUnit.MILLISECONDS.sleep(2);
			} catch (InterruptedException e) {
				// Handle interruption exception
				e.printStackTrace();
			}

			producer.send(record);
		}
		producer.flush();
		producer.close();
	}

}
