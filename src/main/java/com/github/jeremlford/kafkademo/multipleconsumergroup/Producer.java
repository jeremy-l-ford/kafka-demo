package com.github.jeremlford.kafkademo.multipleconsumergroup;

import com.github.jeremlford.kafkademo.Producers;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;

import java.util.Properties;

public class Producer {

    public static void main(String[] args) {
        Properties overrides = new Properties();
        overrides.put(ProducerConfig.PARTITIONER_CLASS_CONFIG, EvenOddPartitioner.class.getName());
        overrides.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, Constants.BOOTSTRAP_SERVERS);

        KafkaProducer<Long, String> producer = Producers.createProducer(overrides);
        Producers.produce(producer, Constants.TOPIC);
    }
}
