package de.hswhameln.typetogether.client.gui;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JTextField;

public class LoginPanel extends AbstractPanel {

    private JTextField text;
    

    public LoginPanel() {
        this.setSize(ViewProperties.DEFAULT_WIDTH, ViewProperties.DEFAULT_HEIGHT);
        this.setPreferredSize(new Dimension(ViewProperties.DEFAULT_WIDTH, ViewProperties.DEFAULT_HEIGHT));
        this.setVisible(true);

        this.text = new JTextField("Das ist ein Test");
        this.text.setForeground(Color.BLACK);
        this.text.setMinimumSize(new Dimension(200, 200));
        this.text.setPreferredSize(new Dimension(200, 200));
        this.text.setVisible(true);
        this.add(this.text);
        this.setBackground(Color.CYAN);
    }
}