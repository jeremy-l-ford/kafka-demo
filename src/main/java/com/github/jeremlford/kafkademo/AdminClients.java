package com.github.jeremlford.kafkademo;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.CreateTopicsResult;
import org.apache.kafka.clients.admin.DeleteTopicsOptions;
import org.apache.kafka.clients.admin.DeleteTopicsResult;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.admin.TopicListing;
import org.apache.kafka.common.KafkaFuture;

import java.util.Collection;
import java.util.Collections;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

public final class AdminClients {

    public static AdminClient createClient() {
        return createClient(null);
    }

    public static AdminClient createClient(Properties overrides) {
        Properties properties = new Properties();
        properties.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, Constants.BOOTSTRAP_SERVERS);

        if (overrides != null) {
            properties.putAll(overrides);
        }

        return AdminClient.create(properties);
    }

    public static void deleteAndCreateTopic(AdminClient adminClient, String topic) throws InterruptedException, ExecutionException {
        deleteAndCreateTopic(adminClient, topic, 1);
    }

    public static void deleteAndCreateTopic(AdminClient adminClient, String topic, int numberOfPartitions) throws InterruptedException, ExecutionException {
        deleteAndCreateTopic(adminClient, new NewTopic(topic, numberOfPartitions, (short) 1));
    }

    public static void deleteAndCreateTopic(AdminClient adminClient, NewTopic newTopic) throws InterruptedException, ExecutionException {
        System.out.println("Topics");
        KafkaFuture<Collection<TopicListing>> listings = adminClient.listTopics().listings();
        System.out.println(adminClient.describeCluster().nodes().get());

        boolean deleteTopic = false;
        for (TopicListing listTopic : listings.get()) {
            if (newTopic.name().equals(listTopic.name())) {
                deleteTopic = true;
            }
            System.out.println(listTopic.name());
        }
        System.out.println("---------");

        if (deleteTopic) {
            System.out.println("Deleting Topic");
            DeleteTopicsResult deleteTopicsResult = adminClient.deleteTopics(Collections.singletonList(newTopic.name()), new DeleteTopicsOptions().timeoutMs(1000));
            deleteTopicsResult.all().get();
            System.out.println("Topic Deleted");
            System.out.println("---------");
        }

        CreateTopicsResult result = adminClient.createTopics(Collections.singletonList(newTopic));
        result.all().get();

        System.out.println("Topic Created");

        adminClient.close();
    }

    private AdminClients() {
    }
}
