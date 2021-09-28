package de.hswhameln.typetogether.client.gui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JPanel;

public class AbstractPanel extends JPanel {

    private List<JComponent> components = new ArrayList<>();

    protected AbstractPanel() {
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
    }

    protected void addComponents(JComponent... components) {
        this.components.addAll(Arrays.asList(components));
        this.components.forEach(this::add);
    }
}
