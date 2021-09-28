package de.hswhameln.typetogether.client.gui;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

public class MenuPanel extends JPanel {
    
    private JPanel headline;
    private JPanel leftSide;
    private JPanel rightSide;

    public MenuPanel() {
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        this.setSize(ViewProperties.DEFAULT_WIDTH, ViewProperties.DEFAULT_HEIGHT);
        this.createGrid();
        this.rightSide.setBackground(ViewProperties.BACKGROUND_COLOR);
        

    }

    private void createGrid() {
        this.headline = new TypeTogetherPanel();
        this.add(this.headline);
        JPanel body = new JPanel();
        body.setLayout(new java.awt.FlowLayout());
        this.add(body);
        this.leftSide = new JPanel();
        body.add(this.leftSide);
        this.rightSide = new JPanel();
        body.add(this.rightSide);
    }

    private void createLeftSide() {

    }
}
