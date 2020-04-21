/*
 * Copyright (c) Mirth Corporation. All rights reserved.
 * 
 * http://www.mirthcorp.com
 * 
 * The software in this package is published under the terms of the MPL license a copy of which has
 * been included with this distribution in the LICENSE.txt file.
 */

package com.mirth.connect.connectors.kafka;

import com.mirth.connect.donkey.model.event.ConnectionStatusEventType;
import com.mirth.connect.donkey.model.event.ErrorEventType;
import com.mirth.connect.donkey.model.message.BatchRawMessage;
import com.mirth.connect.donkey.model.message.RawMessage;
import com.mirth.connect.donkey.server.ConnectorTaskException;
import com.mirth.connect.donkey.server.channel.ChannelException;
import com.mirth.connect.donkey.server.channel.DispatchResult;
import com.mirth.connect.donkey.server.channel.PollConnector;
import com.mirth.connect.donkey.server.event.ConnectionStatusEvent;
import com.mirth.connect.donkey.server.event.ErrorEvent;
import com.mirth.connect.donkey.server.message.batch.BatchMessageException;
import com.mirth.connect.donkey.server.message.batch.BatchMessageReader;
import com.mirth.connect.server.controllers.ContextFactoryController;
import com.mirth.connect.server.controllers.ControllerFactory;
import com.mirth.connect.server.controllers.EventController;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.LongDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

public class KafkaReceiver extends PollConnector {
    private Logger logger = Logger.getLogger(getClass());
    private EventController eventController = ControllerFactory.getFactory().createEventController();
    private ContextFactoryController contextFactoryController = ControllerFactory.getFactory().createContextFactoryController();
    private KafkaReceiverProperties kafkaReceiverProperties;

    @Override
    public void onDeploy() throws ConnectorTaskException {
        this.kafkaReceiverProperties = (KafkaReceiverProperties) getConnectorProperties();
        eventController.dispatchEvent(new ConnectionStatusEvent(getChannelId(), getMetaDataId(), getSourceName(), ConnectionStatusEventType.IDLE));
    }

    @Override
    public void handleRecoveredResponse(DispatchResult dispatchResult) {
        finishDispatch(dispatchResult);
    }

    @Override
    public void onUndeploy() throws ConnectorTaskException {

    }

    @Override
    public void onStart() throws ConnectorTaskException {

    }

    @Override
    public void onStop() throws ConnectorTaskException {

    }

    @Override
    public void onHalt() throws ConnectorTaskException {

    }

    @Override
    protected void poll() throws InterruptedException {
        eventController.dispatchEvent(new ConnectionStatusEvent(getChannelId(), getMetaDataId(), getSourceName(), ConnectionStatusEventType.READING));

        ConsumerRecords<Long, String> records = runConsumer();
        for (RawMessage rawMessage : convert(records)) {
            if (isTerminated()) {
                return;
            }
            try {
                if (isProcessBatch()) {
                    if (rawMessage.isBinary()) {
                        throw new BatchMessageException("Batch processing is not supported for binary data in channel " + getChannelId());
                    } else {
                        BatchRawMessage batchRawMessage = new BatchRawMessage(new BatchMessageReader(rawMessage.getRawData()), rawMessage.getSourceMap());
                        rawMessage = null;
                        dispatchBatchMessage(batchRawMessage, null);
                    }
                } else {
                    DispatchResult dispatchResult = null;
                    try {
                        dispatchResult = dispatchRawMessage(rawMessage);
                    } catch (ChannelException e) {
                        // Do nothing. An error should have been logged.
                    } finally {
                        finishDispatch(dispatchResult);
                    }
                }
            }
            catch (BatchMessageException e) {
                eventController.dispatchEvent(new ErrorEvent(getChannelId(), getMetaDataId(), null, ErrorEventType.SOURCE_CONNECTOR, getSourceName(), kafkaReceiverProperties.getName(), "Error processing batch message", e));
                logger.error(e.getMessage(), e);
            } catch (Throwable t) {
                eventController.dispatchEvent(new ErrorEvent(getChannelId(), getMetaDataId(), null, ErrorEventType.SOURCE_CONNECTOR, getSourceName(), kafkaReceiverProperties.getName(), null, t));
                logger.error("Error polling in channel: " + getChannelId(), t);
            } finally {
                eventController.dispatchEvent(new ConnectionStatusEvent(getChannelId(), getMetaDataId(), getSourceName(), ConnectionStatusEventType.IDLE));
            }
        }


    }
    private ConsumerRecords<Long, String> runConsumer () {
        Consumer<Long, String> consumer = createConsumer();
        ConsumerRecords<Long, String> consumerRecords = consumer.poll(1000);
        consumer.close();
        return consumerRecords;
    }

    private Consumer<Long, String> createConsumer () {
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaReceiverProperties.getBootstrapServers());
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "my-group-id");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, LongDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, kafkaReceiverProperties.getMaxPollRecords());
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, kafkaReceiverProperties.getOffsetReset());
        Consumer<Long, String> consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Collections.singletonList(kafkaReceiverProperties.getTopic()));
        return consumer;
    }

    private List<RawMessage> convert(ConsumerRecords < Long, String > records){
        List<RawMessage> out = new ArrayList<>();
        records.forEach(record -> {
            out.add(new RawMessage(record.value()));
        });

        return out;
    }
}
