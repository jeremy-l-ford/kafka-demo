package com.github.jeremlford.kafkademo.multipleconsumergroup;

class Constants {

    public static final String TOPIC = "kafka-demo-multi";
    public static final String CONSUMER_GROUP_1 = "demo-group1";
    public static final String CONSUMER_GROUP_2 = "demo-group2";
    public static final String BOOTSTRAP_SERVERS = "localhost:9093,localhost:9094";

    private Constants() {
    }
}
