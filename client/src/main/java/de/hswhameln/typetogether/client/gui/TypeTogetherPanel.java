package de.hswhameln.typetogether.client.gui;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class TypeTogetherPanel extends JPanel {

    private JLabel headline;
    
    public TypeTogetherPanel() {
        this.headline = new JLabel(ViewProperties.HEADLINE);
        this.setBackground(ViewProperties.CONTRAST_COLOR);
    }
}