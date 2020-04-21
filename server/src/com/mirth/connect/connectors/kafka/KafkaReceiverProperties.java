/*
 * Copyright (c) Mirth Corporation. All rights reserved.
 * 
 * http://www.mirthcorp.com
 * 
 * The software in this package is published under the terms of the MPL license a copy of which has
 * been included with this distribution in the LICENSE.txt file.
 */

package com.mirth.connect.connectors.kafka;

import com.mirth.connect.donkey.model.channel.*;
import com.mirth.connect.donkey.util.DonkeyElement;

import java.util.Map;

public class KafkaReceiverProperties extends ConnectorProperties implements PollConnectorPropertiesInterface, SourceConnectorPropertiesInterface {
    public static final String NAME = "Kafka Listener";

    private PollConnectorProperties pollConnectorProperties;
    private SourceConnectorProperties sourceConnectorProperties;
    private String bootstrapServers;
    private String topic;
    private Integer maxPollRecords;
    private String offsetReset;

    public KafkaReceiverProperties() {
        pollConnectorProperties = new PollConnectorProperties();
        sourceConnectorProperties = new SourceConnectorProperties();

        bootstrapServers = "localhost:9092";
        topic = "demo-kafka-1";
        maxPollRecords = 10;
        offsetReset = "earliest";
    }

    @Override
    public String getProtocol() { return null; }

    @Override
    public String getName() { return NAME; }

    public String getBootstrapServers() {
        return bootstrapServers;
    }

    public void setBootstrapServers(String bootstrapServers) {
        this.bootstrapServers = bootstrapServers;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public Integer getMaxPollRecords() {
        return maxPollRecords;
    }

    public void setMaxPollRecords(Integer maxPollRecords) {
        this.maxPollRecords = maxPollRecords;
    }

    public String getOffsetReset() {
        return offsetReset;
    }

    public void setOffsetReset(String offsetReset) {
        this.offsetReset = offsetReset;
    }

    @Override
    public String toFormattedString() { return null; }

    @Override
    public boolean equals(Object obj) {
        return false;
    }

    @Override
    public PollConnectorProperties getPollConnectorProperties() {
        return pollConnectorProperties;
    }

    @Override
    public SourceConnectorProperties getSourceConnectorProperties() {
        return sourceConnectorProperties;
    }

    @Override
    public boolean canBatch() {
        return true;
    }

    @Override public void migrate3_0_1(DonkeyElement element) {}
    @Override public void migrate3_0_2(DonkeyElement element) {}

    @Override
    public void migrate3_1_0(DonkeyElement element) { super.migrate3_1_0(element); }

    @Override public void migrate3_2_0(DonkeyElement element) {}
    @Override public void migrate3_3_0(DonkeyElement element) {}
    @Override public void migrate3_4_0(DonkeyElement element) {}
    @Override public void migrate3_5_0(DonkeyElement element) {}
    @Override public void migrate3_6_0(DonkeyElement element) {}
    @Override public void migrate3_7_0(DonkeyElement element) {}

    @Override
    public Map<String, Object> getPurgedProperties() {
        Map<String, Object> purgedProperties = super.getPurgedProperties();
        purgedProperties.put("pollConnectorProperties", pollConnectorProperties.getPurgedProperties());
        purgedProperties.put("sourceConnectorProperties", sourceConnectorProperties.getPurgedProperties());
        return purgedProperties;
    }
}
