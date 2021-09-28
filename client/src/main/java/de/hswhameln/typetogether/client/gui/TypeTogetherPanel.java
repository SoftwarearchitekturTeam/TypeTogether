package de.hswhameln.typetogether.client.gui;

import javax.swing.JLabel;

public class TypeTogetherPanel extends AbstractPanel {

    private JLabel headline;
    
    public TypeTogetherPanel() {
        this.headline = new JLabel(ViewProperties.HEADLINE);
        this.setBackground(ViewProperties.CONTRAST_COLOR);
    }
}