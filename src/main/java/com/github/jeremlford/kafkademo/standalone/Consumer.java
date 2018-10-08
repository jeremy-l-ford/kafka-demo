package com.github.jeremlford.kafkademo.standalone;

import com.github.jeremlford.kafkademo.Consumers;
import org.apache.kafka.clients.consumer.KafkaConsumer;

public class Consumer {

    public static void main(String[] args) {
        KafkaConsumer<Long, String> consumer = Consumers.createConsumer(Constants.TOPIC);
        Consumers.consume(consumer);
    }
}
