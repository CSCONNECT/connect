/*
 * Copyright (c) Mirth Corporation. All rights reserved.
 * 
 * http://www.mirthcorp.com
 * 
 * The software in this package is published under the terms of the MPL license a copy of which has
 * been included with this distribution in the LICENSE.txt file.
 */

package com.mirth.connect.connectors.kafka;

import com.mirth.connect.client.ui.Frame;
import com.mirth.connect.client.ui.PlatformUI;
import com.mirth.connect.client.ui.UIConstants;
import com.mirth.connect.client.ui.components.MirthRadioButton;
import com.mirth.connect.client.ui.components.MirthTextField;
import com.mirth.connect.client.ui.panels.connectors.ConnectorSettingsPanel;
import com.mirth.connect.donkey.model.channel.ConnectorProperties;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;

public class KafkaListener extends ConnectorSettingsPanel {
    private Frame parent;

    public KafkaListener() {
        this.parent = PlatformUI.MIRTH_FRAME;
        initComponents();
        initLayout();
        setProperties(new KafkaReceiverProperties());
    }

    private void initComponents() {
        setBackground(UIConstants.BACKGROUND_COLOR);
        topicLabel = new JLabel("Topic:");
        topicField = new MirthTextField();
        groupIdLabel = new JLabel("Group ID:");
        groupIdField = new MirthTextField();

        offsetResetLabel = new JLabel("Offset Reset:");
        ButtonGroup offsetResetGroup = new ButtonGroup();

        offsetEarliestRadio = new MirthRadioButton("Earliest");
        offsetEarliestRadio.setBackground(getBackground());
        offsetEarliestRadio.addActionListener((event) -> actionOffsetEarliestRadio());
        offsetResetGroup.add(offsetEarliestRadio);

        offsetLatestRadio = new MirthRadioButton("Latest");
        offsetLatestRadio.setBackground(getBackground());
        offsetLatestRadio.addActionListener((event) -> actionOffsetLatestRadio());
        offsetResetGroup.add(offsetLatestRadio);

//        offsetResetField = new MirthTextField();
        maxPollRecordsLabel = new JLabel("Max Poll Records:");
        maxPollRecordsField = new MirthTextField();
        bootstrapServerLabel = new JLabel("Bootstrap Server:");
        bootstrapServerField = new MirthTextField();
    }

    private void initLayout() {
        setLayout(new MigLayout("novisualpadding, hidemode 3, insets 0", "[right]12[left]"));

        add(bootstrapServerLabel, "cell 0 0, alignx right");
        add(bootstrapServerField, "cell 1 0, w 400!, wrap");

        add(groupIdLabel, "cell 0 1, alignx right");
        add(groupIdField, "cell 1 1, w 100!, wrap");

        add(topicLabel, "cell 0 2, alignx right");
        add(topicField, "cell 1 2, w 100!, wrap");

        add(maxPollRecordsLabel, "cell 0 3, alignx right");
        add(maxPollRecordsField, "cell 1 3, w 30!, wrap");

        add(offsetResetLabel, "cell 0 4, alignx right");
        add(offsetEarliestRadio, "cell 1 4");
        add(offsetLatestRadio, "cell 1 4");
//        add(offsetResetField, "w 100!, wrap");
    }

    @Override
    public String getConnectorName() {
        return new KafkaReceiverProperties().getName();
    }

    @Override
    public ConnectorProperties getProperties() {
        KafkaReceiverProperties kafkaReceiverProperties = new KafkaReceiverProperties();
        kafkaReceiverProperties.setBootstrapServers(bootstrapServerField.getText());
        kafkaReceiverProperties.setGroupId(groupIdField.getText());
        kafkaReceiverProperties.setTopic(topicField.getText());
        kafkaReceiverProperties.setMaxPollRecords(Integer.parseInt(maxPollRecordsField.getText()));
        kafkaReceiverProperties.setOffsetResetEarliest(offsetEarliestRadio.isSelected());
        return kafkaReceiverProperties;
    }


    @Override
    public void setProperties(ConnectorProperties properties) {
        KafkaReceiverProperties props = (KafkaReceiverProperties) properties;
        bootstrapServerField.setText(props.getBootstrapServers());
        groupIdField.setText(props.getGroupId());
        topicField.setText(props.getTopic());
        maxPollRecordsField.setText(props.getMaxPollRecords().toString());
        setOffsetReset(props.isOffsetResetEarliest());
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

    private void setOffsetReset(Boolean isOffsetResetEarliest){
        if (isOffsetResetEarliest) {
            offsetLatestRadio.setSelected(false);
            offsetEarliestRadio.setSelected(true);
        } else {
            offsetLatestRadio.setSelected(true);
            offsetEarliestRadio.setSelected(false);
        }
    }
    private void actionOffsetLatestRadio(){
        setOffsetReset(false);
    }
    private void actionOffsetEarliestRadio(){
        setOffsetReset(true);
    }

    private JLabel bootstrapServerLabel;
    private MirthTextField bootstrapServerField;
    private JLabel groupIdLabel;
    private MirthTextField groupIdField;
    private JLabel topicLabel;
    private MirthTextField topicField;
    private JLabel maxPollRecordsLabel;
    private MirthTextField maxPollRecordsField;
    private JLabel offsetResetLabel;
    private MirthRadioButton offsetEarliestRadio;
    private MirthRadioButton offsetLatestRadio;
//    private MirthTextField offsetResetField;
}
