package de.hswhameln.typetogether.client.gui;

import de.hswhameln.typetogether.client.runtime.SessionStorage;
import de.hswhameln.typetogether.networking.LocalDocument;
import de.hswhameln.typetogether.networking.api.Document;
import de.hswhameln.typetogether.networking.api.Lobby;
import de.hswhameln.typetogether.networking.api.exceptions.InvalidDocumentIdException;
import de.hswhameln.typetogether.networking.util.ExceptionHandler;

import javax.swing.*;
import java.awt.*;
import java.util.logging.Level;

public class MenuPanel extends AbstractPanel {

    private JPanel leftSide;
    private JTextField documentNameField;
    private final Lobby lobby;

    public MenuPanel(MainWindow window, SessionStorage sessionStorage) {
        super(window, sessionStorage);
        this.lobby = sessionStorage.getLobby();

        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        this.setSize(ViewProperties.DEFAULT_WIDTH, ViewProperties.DEFAULT_HEIGHT);
        this.createGrid();
        this.createLeftSide();
    }

    private void createGrid() {
        JPanel headline = new TypeTogetherPanel();
        this.add(headline);
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
        JPanel rightSide = new JPanel();
        rightSide.setSize(size);
        rightSide.setPreferredSize(size);
        rightSide.setVisible(true);
        rightSide.setBorder(BorderFactory.createEmptyBorder());
        rightSide.setBackground(ViewProperties.BACKGROUND_COLOR);
        body.add(rightSide);
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
        createDocumentButton.setBackground(ViewProperties.CONTRAST_COLOR);
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

        System.out.println("Create");
        String documentName = this.documentNameField.getText();
        if (documentName.isBlank()) {
            this.window.alert("Geben Sie einen Dokumentnamen ein!", JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            this.sessionStorage.getLobby().createDocument(documentName);
        } catch (InvalidDocumentIdException.DocumentAlreadyExistsException e) {
            ExceptionHandler.getExceptionHandler().handle(e, Level.INFO, "Document already exists.", MenuPanel.class);
            this.window.alert("Document already exists!", JOptionPane.ERROR_MESSAGE);
            return;
        }

        this.joinDocument();
    }

    private void joinDocument() {
        System.out.println("Join");
        String documentName = this.documentNameField.getText();
        if (documentName.isBlank()) {
            this.window.alert("Geben Sie einen Dokumentnamen ein!", JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            Document document = this.lobby.getDocumentById(documentName);

            LocalDocument localDocument = new LocalDocument();
            this.sessionStorage.getCurrentUser().setDocument(localDocument);
            this.sessionStorage.setCurrentSharedDocument(document);

            this.window.switchToView(ViewProperties.EDITOR);
            this.lobby.joinDocument(this.sessionStorage.getCurrentUser(), documentName);
        } catch (InvalidDocumentIdException.DocumentDoesNotExistException e) {
            ExceptionHandler.getExceptionHandler().handle(e, Level.INFO, "Could not join document.", MenuPanel.class);
            this.window.alert("Document " + documentName + " does not exist!", JOptionPane.ERROR_MESSAGE);
        }
    }
}
