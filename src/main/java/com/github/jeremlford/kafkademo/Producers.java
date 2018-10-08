package com.github.jeremlford.kafkademo;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.LongSerializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

public final class Producers {

    public static <K, V> KafkaProducer<K, V> createProducer() {
        return createProducer(null);
    }

    public static <K, V> KafkaProducer<K, V> createProducer(Properties overrides) {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, Constants.BOOTSTRAP_SERVERS);
        props.put(ProducerConfig.CLIENT_ID_CONFIG, "myProducerId");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, LongSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        if (overrides != null) {
            props.putAll(overrides);
        }


        return new KafkaProducer<>(props);
    }

    public static void produce(KafkaProducer<Long, String> producer, String topic) {

        for (int i = 0; i < 50; ++i) {
            long key = i;
            String value = String.format("value:%d", i);
            System.out.println(String.format("Publishing key=%d value=%s", key, value));
            ProducerRecord<Long, String>  record = new ProducerRecord<>(topic, key, value);

            try {
                producer.send(record).get();
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        }

        producer.close();
        System.out.println("DONE");
    }

    private Producers() {
    }
}
