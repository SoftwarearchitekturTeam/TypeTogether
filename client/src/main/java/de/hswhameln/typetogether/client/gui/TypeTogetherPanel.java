package de.hswhameln.typetogether.client.gui;

import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.text.View;

public class TypeTogetherPanel extends JPanel {

    private JLabel headline;
    
    public TypeTogetherPanel() {
        this.headline = new JLabel(ViewProperties.HEADLINE);
        this.headline.setFont(ViewProperties.HEADLINE_FONT);
        this.setBackground(ViewProperties.CONTRAST_COLOR);
        Dimension size = new Dimension(ViewProperties.DEFAULT_WIDTH, ViewProperties.HEADLINE_HEIGHT);
        this.setMaximumSize(size);
        this.setSize(size);
        this.setMinimumSize(size);
        this.setPreferredSize(size);
                this.add(headline);
        this.setVisible(true);
    }
}