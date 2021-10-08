package de.hswhameln.typetogether.client.gui;

import de.hswhameln.typetogether.client.runtime.PropertyChangeManager;
import de.hswhameln.typetogether.client.runtime.SessionStorage;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AbstractPanel extends JPanel {

    private final List<Component> components = new ArrayList<>();
    private final TypeTogetherPanel headerPanel = new TypeTogetherPanel();

    protected PropertyChangeManager propertyChangeManager;
    protected final MainWindow window;
    protected final SessionStorage sessionStorage;

    protected AbstractPanel(MainWindow window, SessionStorage sessionStorage) {
        this.window = window;
        this.sessionStorage = sessionStorage;

        this.propertyChangeManager = new PropertyChangeManager();
        this.sessionStorage.addPropertyChangeListener(this.propertyChangeManager);

        this.setSize(ViewProperties.DEFAULT_WIDTH, ViewProperties.DEFAULT_HEIGHT);
        this.setPreferredSize(new Dimension(ViewProperties.DEFAULT_WIDTH, ViewProperties.DEFAULT_HEIGHT));
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setVisible(true);
    }

    protected void addComponents(Component... components) {
        this.components.addAll(Arrays.asList(components));
        this.add(headerPanel);
        this.components.forEach(this::add);
        this.components.forEach(component -> component.setVisible(true));
    }

    public void initialize() {

    }

    public void windowClosed() {
    }
}
