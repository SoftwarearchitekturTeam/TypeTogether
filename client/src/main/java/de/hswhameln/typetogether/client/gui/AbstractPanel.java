package de.hswhameln.typetogether.client.gui;

import java.awt.Component;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

public class AbstractPanel extends JPanel {

    private final List<Component> components = new ArrayList<>();
	private final TypeTogetherPanel headerPanel = new TypeTogetherPanel();

    protected final MainWindow window;

    protected AbstractPanel(MainWindow window) {
        this.setSize(ViewProperties.DEFAULT_WIDTH, ViewProperties.DEFAULT_HEIGHT);
        this.setPreferredSize(new Dimension(ViewProperties.DEFAULT_WIDTH, ViewProperties.DEFAULT_HEIGHT));
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setVisible(true);
        this.window = window;
    }

    protected void addComponents(Component... components) {
        this.components.addAll(Arrays.asList(components));
        this.add(headerPanel);
        this.components.forEach(this::add);
        this.components.forEach(component -> component.setVisible(true));
    }

    public void initialize() {

    }
}
