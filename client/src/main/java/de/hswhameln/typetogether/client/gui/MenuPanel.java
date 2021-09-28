package de.hswhameln.typetogether.client.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class MenuPanel extends AbstractPanel {
    
    private MainWindow window;
    private JPanel headline;
    private JPanel leftSide;
    private JPanel rightSide;

    private JTextField documentNameField;

    public MenuPanel(MainWindow window) {
        this.window = window;
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        this.setSize(ViewProperties.DEFAULT_WIDTH, ViewProperties.DEFAULT_HEIGHT);
        this.createGrid();
        this.rightSide.setBackground(Color.CYAN);
        this.leftSide.setBackground(Color.GREEN);
        this.leftSide.setLayout(new BoxLayout(this.leftSide, BoxLayout.Y_AXIS));
        this.createLeftSide();
    }

    private void createGrid() {
        this.headline = new TypeTogetherPanel();
        this.add(this.headline);
        headline.setVisible(true);
        headline.setBorder(BorderFactory.createEmptyBorder());
        JPanel body = new JPanel();
        FlowLayout flowLayout = new FlowLayout();
        flowLayout.setVgap(0);
        flowLayout.setHgap(0);
        flowLayout.setAlignment(FlowLayout.LEFT);
        body.setLayout(flowLayout);
        body.setVisible(true);
        body.setBorder(BorderFactory.createEmptyBorder());
        this.add(body);
        this.leftSide = new JPanel();

        Dimension size = new Dimension(ViewProperties.DEFAULT_WIDTH / 2 - 6, ViewProperties.DEFAULT_HEIGHT - ViewProperties.HEADLINE_HEIGHT);
        this.leftSide.setSize(size);
        this.leftSide.setPreferredSize(size);
        this.leftSide.setVisible(true);
        this.leftSide.setBorder(BorderFactory.createEmptyBorder());
        body.add(this.leftSide);
        this.rightSide = new JPanel();
        this.rightSide.setSize(size);
        this.rightSide.setPreferredSize(size);
        this.rightSide.setVisible(true);
        this.rightSide.setBorder(BorderFactory.createEmptyBorder());
        body.add(this.rightSide);
    }

    private void createLeftSide() {
        JLabel documentTitle = new JLabel("Name des Dokuments");
        Dimension sizeTitle = new Dimension(200, 70);
        documentTitle.setMaximumSize(sizeTitle);
        documentTitle.setVisible(true);
        documentTitle.setFont(ViewProperties.SUBHEADLINE_FONT);
        documentTitle.setForeground(ViewProperties.FONT_COLOR);
        documentTitle.setBackground(ViewProperties.BACKGROUND_COLOR);
        this.leftSide.add(documentTitle);

        this.documentNameField = new JTextField(2);
        this.documentNameField.setForeground(ViewProperties.FONT_COLOR);
        this.documentNameField.setMaximumSize(new Dimension(300, 50));
        this.documentNameField.setBorder(BorderFactory.createLineBorder(ViewProperties.FONT_COLOR, 1));
        this.leftSide.add(this.documentNameField);

        JButton createDocumentButton = new JButton("Erstellen");
        createDocumentButton.setForeground(ViewProperties.BACKGROUND_COLOR);
        createDocumentButton.setBackground(ViewProperties.GREY_BUTTON_COLOR);
        createDocumentButton.setMaximumSize(new Dimension(120, 50));
        createDocumentButton.setBorder(BorderFactory.createEmptyBorder());
        createDocumentButton.addActionListener(a -> this.createDocument());
        this.leftSide.add(createDocumentButton);

        JButton joinDocumentButton = new JButton("Beitreten");
        joinDocumentButton.setForeground(ViewProperties.BACKGROUND_COLOR);
        joinDocumentButton.setBackground(ViewProperties.CONTRAST_COLOR);
        joinDocumentButton.setMaximumSize(new Dimension(120, 50));
        joinDocumentButton.setBorder(BorderFactory.createEmptyBorder());
        joinDocumentButton.addActionListener(a -> this.joinDocument());
        this.leftSide.add(joinDocumentButton);
    }

    private void createDocument() {
        System.out.println("Create");
    }

    private void joinDocument() {
        System.out.println("Join");
        this.window.switchToView(ViewProperties.EDITOR);
    }
}
