package com.github.jeremlford.kafkademo.standalone;

import com.github.jeremlford.kafkademo.Producers;
import org.apache.kafka.clients.producer.KafkaProducer;

public class Producer {

    public static void main(String[] args) {
        KafkaProducer<Long, String> producer = Producers.createProducer();
        Producers.produce(producer, Constants.TOPIC);
    }
}
