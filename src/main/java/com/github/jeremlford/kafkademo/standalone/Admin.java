package com.github.jeremlford.kafkademo.standalone;

import com.github.jeremlford.kafkademo.AdminClients;
import org.apache.kafka.clients.admin.*;

public class Admin {

    public static void main(String[] args) throws Exception {
        AdminClient adminClient = AdminClients.createClient();
        AdminClients.deleteAndCreateTopic(adminClient, Constants.TOPIC);
    }
}
