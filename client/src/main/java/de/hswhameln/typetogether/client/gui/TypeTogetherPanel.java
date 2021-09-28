package de.hswhameln.typetogether.client.gui;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class TypeTogetherPanel extends JPanel {

    private JLabel headline;
    
    public TypeTogetherPanel() {
        this.headline = new JLabel(ViewProperties.HEADLINE)
        this.setBackground(ViewProperties.CONTRAST_COLOR);
    }
}