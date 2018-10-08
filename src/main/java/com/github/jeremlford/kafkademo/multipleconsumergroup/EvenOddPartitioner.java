package com.github.jeremlford.kafkademo.multipleconsumergroup;

import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;
import org.apache.kafka.common.PartitionInfo;

import java.util.List;
import java.util.Map;

public class EvenOddPartitioner implements Partitioner {

    @Override
    public int partition(String topic, Object key, byte[] keyBytes, Object value, byte[] valueBytes, Cluster cluster) {
        long partition = (long) key;

        List<PartitionInfo> partitionInfoList = cluster.availablePartitionsForTopic(topic);

        if (partitionInfoList.size() != 2) {
            throw new IllegalStateException("2 partitions are required.  found " + partitionInfoList.size());
        }

        return (int) partition % 2;
    }

    @Override
    public void close() {

    }

    @Override
    public void configure(Map<String, ?> configs) {

    }
}
