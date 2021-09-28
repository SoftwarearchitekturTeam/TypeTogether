package de.hswhameln.typetogether.client.gui;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class MenuPanel extends AbstractPanel {
    
    private JPanel headline;
    private JPanel leftSide;
    private JPanel rightSide;

    private JTextArea documentNameArea;

    public MenuPanel() {
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        this.setSize(ViewProperties.DEFAULT_WIDTH, ViewProperties.DEFAULT_HEIGHT);
        this.createGrid();
        this.rightSide.setBackground(ViewProperties.BACKGROUND_COLOR);
        this.leftSide.setBackground(ViewProperties.BACKGROUND_COLOR);

    }

    private void createGrid() {
        this.headline = new TypeTogetherPanel();
        this.add(this.headline);
        headline.setVisible(true);
        JPanel body = new JPanel();
        body.setLayout(new java.awt.FlowLayout());
        body.setVisible(true);
        this.add(body);
        this.leftSide = new JPanel();
        this.leftSide.setVisible(true);
        body.add(this.leftSide);
        this.rightSide = new JPanel();
        this.rightSide.setVisible(true);
        body.add(this.rightSide);
    }

    private void createLeftSide() {
        JLabel documentTitle = new JLabel("Name des Dokuments");
        documentTitle.setFont(ViewProperties.SUBHEADLINE_FONT);
        documentTitle.setForeground(ViewProperties.FONT_COLOR);
        this.leftSide.add(documentTitle);

        this.documentNameArea = new JTextArea();
        this.documentNameArea.setColumns(1);
        this.documentNameArea.setForeground(ViewProperties.FONT_COLOR);
        this.documentNameArea.setSize(300, 70);
        this.leftSide.add(this.documentNameArea);

        JButton createDocumentButton = new JButton("Erstellen");
        createDocumentButton.setForeground(ViewProperties.BACKGROUND_COLOR);
        createDocumentButton.setBackground(ViewProperties.GREY_BUTTON_COLOR);
        createDocumentButton.setSize(120, 70);
        createDocumentButton.addActionListener(a -> this.createDocument());
        this.leftSide.add(createDocumentButton);

        JButton joinDocumentButton = new JButton("Beitreten");
        joinDocumentButton.setForeground(ViewProperties.BACKGROUND_COLOR);
        joinDocumentButton.setBackground(ViewProperties.CONTRAST_COLOR);
        joinDocumentButton.setSize(120, 70);
        joinDocumentButton.addActionListener(a -> this.joinDocument());
        this.leftSide.add(joinDocumentButton);
    }

    private void createDocument() {
        System.out.println("Create");
    }

    private void joinDocument() {
        System.out.println("Join");
    }
}
