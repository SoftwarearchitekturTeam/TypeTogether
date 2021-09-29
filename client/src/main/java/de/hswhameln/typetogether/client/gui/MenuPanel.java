package de.hswhameln.typetogether.client.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import de.hswhameln.typetogether.networking.LocalDocument;
import de.hswhameln.typetogether.client.runtime.ClientRuntime;
import de.hswhameln.typetogether.networking.api.Document;
import de.hswhameln.typetogether.networking.api.Lobby;
import de.hswhameln.typetogether.networking.util.ExceptionHandler;

public class MenuPanel extends AbstractPanel {
    
    private JPanel headline;
    private JPanel leftSide;
    private JPanel rightSide;

    private JTextField documentNameField;

    public MenuPanel(MainWindow window) {
        super(window);
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        this.setSize(ViewProperties.DEFAULT_WIDTH, ViewProperties.DEFAULT_HEIGHT);
        this.createGrid();
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
        this.leftSide.setBackground(ViewProperties.BACKGROUND_COLOR);
        body.add(this.leftSide);
        this.rightSide = new JPanel();
        this.rightSide.setSize(size);
        this.rightSide.setPreferredSize(size);
        this.rightSide.setVisible(true);
        this.rightSide.setBorder(BorderFactory.createEmptyBorder());
        this.rightSide.setBackground(ViewProperties.BACKGROUND_COLOR);
        body.add(this.rightSide);
    }

    private void createLeftSide() {
        BoxLayout layout = new BoxLayout(this.leftSide, BoxLayout.Y_AXIS);
        this.leftSide.setLayout(layout);
        this.leftSide.add(Box.createVerticalStrut(150));
        
        JLabel documentTitle = new JLabel("Name des Dokuments");
        Dimension sizeTitle = new Dimension(200, 70);
        documentTitle.setMaximumSize(sizeTitle);
        documentTitle.setMinimumSize(sizeTitle);
        documentTitle.setVisible(true);
        documentTitle.setAlignmentX(100);
        documentTitle.setHorizontalTextPosition(SwingConstants.LEFT);
        documentTitle.setFont(ViewProperties.SUBHEADLINE_FONT);
        documentTitle.setForeground(ViewProperties.FONT_COLOR);
        documentTitle.setBackground(Color.CYAN);
        this.leftSide.add(documentTitle);

        this.documentNameField = new JTextField(2);
        this.documentNameField.setForeground(ViewProperties.FONT_COLOR);
        this.documentNameField.setSize(500, 40);
        this.documentNameField.setMaximumSize(new Dimension(500, 40));
        this.documentNameField.setFont(ViewProperties.EDITOR_FONT);
        this.documentNameField.setBorder(BorderFactory.createLineBorder(ViewProperties.FONT_COLOR, 1));
        this.leftSide.add(this.documentNameField);

        this.leftSide.add(Box.createRigidArea(new Dimension(500, 15)));

        this.leftSide.add(this.createButtons(new Dimension(500, 50)));
    }

    private JPanel createButtons(Dimension panelSize) {
        JPanel buttons = new JPanel();
        buttons.setVisible(true);
        buttons.setBackground(ViewProperties.BACKGROUND_COLOR);
        buttons.setMaximumSize(panelSize);
        buttons.setPreferredSize(panelSize);
        buttons.setMinimumSize(panelSize);
        buttons.setSize(panelSize);
        FlowLayout flowLayout = new FlowLayout();
        flowLayout.setVgap(0);
        flowLayout.setHgap(0);
        flowLayout.setAlignment(FlowLayout.LEFT);
        buttons.setLayout(flowLayout);

        Dimension buttonSize = new Dimension(150, 50);
        JButton createDocumentButton = new JButton("Erstellen");
        createDocumentButton.setFont(ViewProperties.SUBHEADLINE_FONT);
        createDocumentButton.setForeground(ViewProperties.BACKGROUND_COLOR);
        createDocumentButton.setBackground(ViewProperties.GREY_BUTTON_COLOR);
        createDocumentButton.setMinimumSize(buttonSize);
        createDocumentButton.setMaximumSize(buttonSize);
        createDocumentButton.setPreferredSize(buttonSize);
        createDocumentButton.setSize(buttonSize);
        createDocumentButton.setBorder(BorderFactory.createEmptyBorder());
        createDocumentButton.addActionListener(a -> this.createDocument());
        buttons.add(createDocumentButton);

        buttons.add(Box.createHorizontalStrut(200));

        JButton joinDocumentButton = new JButton("Beitreten");
        joinDocumentButton.setFont(ViewProperties.SUBHEADLINE_FONT);
        joinDocumentButton.setForeground(ViewProperties.BACKGROUND_COLOR);
        joinDocumentButton.setBackground(ViewProperties.CONTRAST_COLOR);
        joinDocumentButton.setMinimumSize(buttonSize);
        joinDocumentButton.setMaximumSize(buttonSize);
        joinDocumentButton.setPreferredSize(buttonSize);
        joinDocumentButton.setSize(buttonSize);
        joinDocumentButton.setBorder(BorderFactory.createEmptyBorder());
        joinDocumentButton.addActionListener(a -> this.joinDocument());
        buttons.add(joinDocumentButton);
        return buttons;
    }

    private void createDocument() {

        //Create localDocument sender
        //Add leerzeile as first char with [0, 0]
        System.out.println("Create");
    }

    private void joinDocument() {
        System.out.println("Join");
        String documentName = this.documentNameField.getText();
        if(!documentName.isEmpty()) {
            try {
                ClientRuntime runtime = this.window.getClientRuntime();
                Lobby lobby = runtime.getLobby();
                Document document = lobby.joinDocument(runtime.getUser(), documentName);
                runtime.setLocalDocument(new LocalDocument());
                runtime.generateSender(document);
                this.window.switchToView(ViewProperties.EDITOR);
            } catch(Exception e) {
                ExceptionHandler.getExceptionHandler().handle(e, "Error while joining Document", MenuPanel.class);
                this.window.alert("Geben sie einen gültigen Dokumentnamen ein!", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            this.window.alert("Geben Sie einen gültigen Dokumentnamen ein!", JOptionPane.WARNING_MESSAGE);
        }
    }
}
