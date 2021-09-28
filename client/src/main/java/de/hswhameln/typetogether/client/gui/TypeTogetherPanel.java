package de.hswhameln.typetogether.client.gui;

import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class TypeTogetherPanel extends JPanel {

    private JLabel headline;
    
    public TypeTogetherPanel() {
        this.headline = new JLabel(ViewProperties.HEADLINE);
        this.headline.setFont(ViewProperties.HEADLINE_FONT);
        this.setBackground(ViewProperties.CONTRAST_COLOR);
        this.setMaximumSize(new Dimension(ViewProperties.DEFAULT_WIDTH * 2, 100));
        this.add(headline);
        this.setVisible(true);
    }
}