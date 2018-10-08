package com.github.jeremlford.kafkademo;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.LongDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public final class Consumers {

    public static <K, V> KafkaConsumer<K, V> createConsumer(String topic) {
        return createConsumer(topic, null);
    }

    public static <K, V> KafkaConsumer<K, V> createConsumer(String topic, Properties overrides) {
        final Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, Constants.BOOTSTRAP_SERVERS);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, Constants.DEFAULT_GROUP_ID);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, LongDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest"); //earliest
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, true);
        props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, String.valueOf(TimeUnit.SECONDS.toMillis(1)));


        if (overrides != null) {
            props.putAll(overrides);
        }

        // Create the consumer using props.
        KafkaConsumer<K, V> consumer = new KafkaConsumer<>(props);

        consumer.subscribe(
                Collections.singletonList(
                        topic
                )
        );

        for (TopicPartition topicPartition : consumer.assignment()) {
            System.out.printf("partition:%d, topic:%s", topicPartition.partition(), topicPartition.topic());
        }

        return consumer;
    }

    public static <K, V> void consume(KafkaConsumer<K, V> consumer) {

        final int giveUp = 100;
        int noRecordsCount = 0;

        while (true) {

            final ConsumerRecords<K, V> consumerRecords = consumer.poll(Duration.ofMillis(1000));

            if (consumerRecords.count() == 0) {
                noRecordsCount++;
                if (noRecordsCount > giveUp) break;
                else continue;
            }

            consumerRecords.forEach(record -> {
                System.out.println("Record Key " + record.key());
                System.out.println("Record value " + record.value());
                System.out.println("Record partition " + record.partition());
                System.out.println("Record offset " + record.offset());
                System.out.println();
            });

        }
        consumer.close();
        System.out.println("DONE");
    }

    private Consumers() {

    }
}
