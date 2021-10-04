package de.hswhameln.typetogether.client.gui.util;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;

import javax.swing.JButton;

import de.hswhameln.typetogether.client.gui.ViewProperties;

public class ButtonFactory {
    private ButtonFactory() {

    }

    public static JButton createLeftButton(String label, Runnable run) {
        JButton button = createButton(label, run);
        button.setAlignmentX(Component.LEFT_ALIGNMENT);
        return button; 
    }

    public static JButton createRightButton(String label, Runnable run) {
        JButton button = createButton(label, run);
        button.setAlignmentX(Component.RIGHT_ALIGNMENT);
        return button;
    }

    public static JButton createCenterButtion(String label, Runnable run) {
        JButton button = createButton(label, run);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        return button;
    }

    public static JButton createButton(String label, Runnable run) {
        JButton button = new JButton(label);
        button.addActionListener(a -> run.run());
        button.setBackground(ViewProperties.CONTRAST_COLOR);
        button.setForeground(ViewProperties.BACKGROUND_COLOR);
        button.setMaximumSize(new Dimension(200, 50));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setHorizontalAlignment(JButton.CENTER);
        return button;
    }
}
