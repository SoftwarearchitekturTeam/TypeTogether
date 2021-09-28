package de.hswhameln.typetogether.client.gui.util;

import java.awt.Dimension;
import java.awt.Component;
import java.awt.Cursor;

import javax.swing.JButton;

import de.hswhameln.typetogether.client.gui.ViewProperties;

public class ButtonFactory {
    private ButtonFactory() {

    }

    public static JButton createButton(String label) {
        JButton button = new JButton(label);
        button.setBackground(ViewProperties.CONTRAST_COLOR);
        button.setMaximumSize(new Dimension(200, 50));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setHorizontalAlignment(JButton.CENTER);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        return button;
    }
}
