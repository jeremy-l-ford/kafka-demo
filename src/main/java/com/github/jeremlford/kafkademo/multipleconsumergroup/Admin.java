package com.github.jeremlford.kafkademo.multipleconsumergroup;

import com.github.jeremlford.kafkademo.AdminClients;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;

import java.util.Properties;

public class Admin {

    public static void main(String[] args) throws Exception {
        Properties overrides = new Properties();
        overrides.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, Constants.BOOTSTRAP_SERVERS);

        AdminClient adminClient = AdminClients.createClient(overrides);
        AdminClients.deleteAndCreateTopic(adminClient, new NewTopic(Constants.TOPIC, 2, (short)2));
    }
}
