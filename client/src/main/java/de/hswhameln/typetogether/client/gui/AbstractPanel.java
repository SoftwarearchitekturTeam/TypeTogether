package de.hswhameln.typetogether.client.gui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JPanel;

public class AbstractPanel extends JPanel {

    private List<JComponent> components = new ArrayList<>();
	private TypeTogetherPanel headerPanel = new TypeTogetherPanel();

    protected AbstractPanel() {
        this.setSize(ViewProperties.DEFAULT_WIDTH, ViewProperties.DEFAULT_HEIGHT);
        this.setPreferredSize(new Dimension(ViewProperties.DEFAULT_WIDTH, ViewProperties.DEFAULT_HEIGHT));
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setVisible(true);
    }

    protected void addComponents(JComponent... components) {
        this.components.addAll(Arrays.asList(components));
        this.add(headerPanel);
        this.components.forEach(this::add);
        this.components.forEach(component -> component.setVisible(true));
    }
}
