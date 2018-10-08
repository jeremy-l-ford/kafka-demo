package com.github.jeremlford.kafkademo.multipleconsumergroup;


import com.github.jeremlford.kafkademo.Consumers;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.ProducerConfig;

import java.util.Properties;

public class Consumer1 {

    public static void main(String[] args) {
        Properties overrides = new Properties();
        overrides.put(ConsumerConfig.GROUP_ID_CONFIG, Constants.CONSUMER_GROUP_1);
        overrides.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, Constants.BOOTSTRAP_SERVERS);

        KafkaConsumer<Long, String> consumer = Consumers.createConsumer(Constants.TOPIC, overrides);
        Consumers.consume(consumer);
    }


}
