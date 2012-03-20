/*
 * Copyright (c) Mirth Corporation. All rights reserved.
 * http://www.mirthcorp.com
 *
 * The software in this package is published under the terms of the MPL
 * license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 */

package com.mirth.connect.client.ui;

import java.awt.Dimension;
import java.awt.Point;
import java.io.UnsupportedEncodingException;

import javax.swing.Icon;
import javax.swing.UIManager;

/** Creates the error dialog. */
public class ErrorDialog extends javax.swing.JDialog {

    private Frame parent;

    public ErrorDialog(java.awt.Frame owner, String message) {
        super(owner);
        initialize(message);
    }

    public ErrorDialog(java.awt.Dialog owner, String message) {
        super(owner);
        initialize(message);
    }

    private void initialize(String message) {
        this.parent = PlatformUI.MIRTH_FRAME;
        initComponents();
        question.setBackground(UIManager.getColor("Control"));
        errorContent.setBackground(UIManager.getColor("Control"));
        image.setIcon((Icon) UIManager.get("OptionPane.errorIcon"));
        questionPane.setBorder(null);
        try {
            message = java.net.URLDecoder.decode(message, "UTF-8");
        } catch (UnsupportedEncodingException e) {
        }
        loadContent(message);
        errorContent.setCaretPosition(0);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setModal(true);
        pack();
        Dimension dlgSize = getPreferredSize();
        Dimension frmSize = parent.getSize();
        Point loc = parent.getLocation();

        if ((frmSize.width == 0 && frmSize.height == 0) || (loc.x == 0 && loc.y == 0)) {
            setLocationRelativeTo(null);
        } else {
            setLocation((frmSize.width - dlgSize.width) / 2 + loc.x, (frmSize.height - dlgSize.height) / 2 + loc.y);
        }

        cancel.requestFocusInWindow();
        setVisible(true);
    }

    /** Loads the contents of the error */
    public void loadContent(String message) {
        errorContent.setText(message);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code
    // <editor-fold defaultstate="collapsed" desc=" Generated Code
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        cancel = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        errorContent = new javax.swing.JTextPane();
        questionPane = new javax.swing.JScrollPane();
        question = new javax.swing.JTextPane();
        image = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Error");

        cancel.setText("Close");
        cancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelActionPerformed(evt);
            }
        });

        errorContent.setBackground(new java.awt.Color(224, 223, 227));
        errorContent.setEditable(false);
        jScrollPane1.setViewportView(errorContent);

        question.setBackground(new java.awt.Color(224, 223, 227));
        question.setBorder(null);
        question.setEditable(false);
        question.setText("An unexpected error has occurred.  If this is a severe error and you are on Mirth Support, please contact the Mirth Help Desk.");
        question.setFocusable(false);
        questionPane.setViewportView(question);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cancel, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(image)
                        .addGap(20, 20, 20)
                        .addComponent(questionPane, javax.swing.GroupLayout.DEFAULT_SIZE, 364, Short.MAX_VALUE)
                        .addGap(20, 20, 20))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 404, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(image, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(questionPane, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cancel)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cancelActionPerformed(java.awt.event.ActionEvent evt)// GEN-FIRST:event_cancelActionPerformed
    {// GEN-HEADEREND:event_cancelActionPerformed
        this.dispose();
    }// GEN-LAST:event_cancelActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cancel;
    private javax.swing.JTextPane errorContent;
    private javax.swing.JLabel image;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextPane question;
    private javax.swing.JScrollPane questionPane;
    // End of variables declaration//GEN-END:variables
}
