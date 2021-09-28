package de.hswhameln.typetogether.client.gui.util;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;

import javax.swing.JButton;

import de.hswhameln.typetogether.client.gui.ViewProperties;

public class ButtonFactory {
    private ButtonFactory() {

    }

    public static JButton createLeftButton(String label) {
        JButton button = createButton(label);
        button.setAlignmentX(Component.LEFT_ALIGNMENT);
        return button; 
    }

    public static JButton createRightButton(String label) {
        JButton button = createButton(label);
        button.setAlignmentX(Component.RIGHT_ALIGNMENT);
        return button;
    }

    public static JButton createCenterButtion(String label) {
        JButton button = createButton(label);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        return button;
    }

    private static JButton createButton(String label) {
        JButton button = new JButton(label);
        button.setBackground(ViewProperties.CONTRAST_COLOR);
        button.setMaximumSize(new Dimension(200, 50));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setHorizontalAlignment(JButton.CENTER);
        return button;
    }
}
