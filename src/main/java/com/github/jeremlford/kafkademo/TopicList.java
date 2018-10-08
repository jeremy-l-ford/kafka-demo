package com.github.jeremlford.kafkademo;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.TopicListing;

import java.util.concurrent.ExecutionException;

public class TopicList {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        AdminClient adminClient = AdminClients.createClient();

        for (TopicListing listTopic : adminClient.listTopics().listings().get()) {
            System.out.println(listTopic.name());
        }
    }
}
