/*
 * Copyright (c) Mirth Corporation. All rights reserved.
 * 
 * http://www.mirthcorp.com
 * 
 * The software in this package is published under the terms of the MPL license a copy of which has
 * been included with this distribution in the LICENSE.txt file.
 */

package com.mirth.connect.connectors.kafka;

import com.mirth.connect.client.ui.UIConstants;
import com.mirth.connect.client.ui.components.MirthTextField;
import com.mirth.connect.client.ui.panels.connectors.ConnectorSettingsPanel;
import com.mirth.connect.donkey.model.channel.ConnectorProperties;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;

public class KafkaListener extends ConnectorSettingsPanel {

    public KafkaListener() {
        initComponents();
        initLayout();
    }

    private void initComponents() {
        setBackground(UIConstants.BACKGROUND_COLOR);
        topicLabel = new JLabel("Topic:");
        topicField = new MirthTextField();
        offsetResetLabel = new JLabel("Offset Reset");
        offsetResetField = new MirthTextField();
        maxPollRecordsLabel = new JLabel("Max Poll Records:");
        maxPollRecordsField = new MirthTextField();
        bootstrapServerLabel = new JLabel("Bootstrap Server:");
        bootstrapServerField = new MirthTextField();
    }

    private void initLayout() {
        setLayout(new MigLayout("novisualpadding, hidemode 3, insets 0", "[right]12[left]"));

        add(bootstrapServerLabel);
        add(bootstrapServerField, "w 400!, wrap");

        add(topicLabel);
        add(topicField, "w 200!, wrap");

        add(maxPollRecordsLabel);
        add(maxPollRecordsField, "w 200!, wrap");

        add(offsetResetLabel);
        add(offsetResetField, "w 200!, wrap");

    }

    @Override
    public String getConnectorName() {
        return new KafkaReceiverProperties().getName();
    }

    @Override
    public ConnectorProperties getProperties() {
        return new KafkaReceiverProperties();
    }

    @Override
    public void setProperties(ConnectorProperties properties) {
        KafkaReceiverProperties props = (KafkaReceiverProperties) properties;
        bootstrapServerField.setText(props.getBootstrapServers());
        topicField.setText(props.getTopic());
        maxPollRecordsField.setText(props.getMaxPollRecords().toString());
        offsetResetField.setText(props.getOffsetReset());
    }

    @Override
    public ConnectorProperties getDefaults() {
        return new KafkaReceiverProperties();
    }

    @Override
    public boolean checkProperties(ConnectorProperties properties, boolean highlight) {
        return true;
    }

    @Override
    public void resetInvalidProperties() {
    }

    private JLabel bootstrapServerLabel;
    private MirthTextField bootstrapServerField;
    private JLabel topicLabel;
    private MirthTextField topicField;
    private JLabel maxPollRecordsLabel;
    private MirthTextField maxPollRecordsField;
    private JLabel offsetResetLabel;
    private MirthTextField offsetResetField;
}
