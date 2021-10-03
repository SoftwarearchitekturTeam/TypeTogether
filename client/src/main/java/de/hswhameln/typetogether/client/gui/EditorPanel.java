package de.hswhameln.typetogether.client.gui;

import de.hswhameln.typetogether.client.businesslogic.ClientUser;
import de.hswhameln.typetogether.client.gui.util.FileHelper;
import de.hswhameln.typetogether.client.runtime.SessionStorage;
import de.hswhameln.typetogether.networking.DocumentObserver;
import de.hswhameln.typetogether.networking.LocalDocument;
import de.hswhameln.typetogether.networking.api.exceptions.InvalidDocumentIdException;
import de.hswhameln.typetogether.networking.api.exceptions.UnknownUserException;
import de.hswhameln.typetogether.networking.util.ExceptionHandler;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

import static de.hswhameln.typetogether.client.gui.util.ButtonFactory.createLeftButton;
import static de.hswhameln.typetogether.client.gui.util.ButtonFactory.createRightButton;

public class EditorPanel extends AbstractPanel {

    private final Logger logger = Logger.getLogger(this.getClass().getName());
    private final DocumentObserver observer;

    private final JTextArea editor;
    private final CustomSwingDocument swingDocument;

    private ClientUser user;
    private LocalDocument localDocument;

    public EditorPanel(MainWindow window, SessionStorage sessionStorage) {
        super(window, sessionStorage);
        this.observer = new DocumentObserver(this::addChar, this::removeChar);
        this.swingDocument = new CustomSwingDocument();
        this.editor = new JTextArea(this.swingDocument, "", 5, 20);
        this.editor.setFont(ViewProperties.EDITOR_FONT);
        this.editor.setHighlighter(null);
        JScrollPane editorPane = new JScrollPane(this.editor);
        editorPane.setMaximumSize(ViewProperties.EDITOR_SIZE);
        editorPane.setBorder(BorderFactory.createEmptyBorder(50, 0, 50, 0));
        JButton leave = createRightButton("Verlassen", this::leaveEditor);
        JButton export = createLeftButton("Exportieren", this::exportText);
        leave.setVisible(true);
        export.setVisible(true);
        JPanel btnPanel = new JPanel();
        btnPanel.setLayout(new FlowLayout());
        btnPanel.setMaximumSize(ViewProperties.BTN_SIZE);

        this.editor.setText("");
        btnPanel.add(leave);
        btnPanel.add(export);
        this.addComponents(editorPane, btnPanel);
        this.swingDocument.addDocumentListener(new EditorListener(sessionStorage));
        
        this.setUser(sessionStorage.getCurrentUser());
        this.propertyChangeManager.onPropertyChange(SessionStorage.CURRENT_USER, this::userChanged);
        this.propertyChangeManager.onPropertyChange(ClientUser.LOCAL_DOCUMENT, this::localDocumentChanged);
    }

    private void userChanged(PropertyChangeEvent propertyChangeEvent) {
        setUser((ClientUser) propertyChangeEvent.getNewValue());
    }

    private void setUser(ClientUser newUser) {
        if (this.user != null) {
            this.user.removePropertyChangeListener(this.propertyChangeManager);
        }
        this.user = newUser;
        if (this.user != null) {
            this.user.addPropertyChangeListener(this.propertyChangeManager);
            this.setLocalDocument((LocalDocument) this.user.getDocument());
        }
    }

    private void localDocumentChanged(PropertyChangeEvent propertyChangeEvent) {
        this.setLocalDocument((LocalDocument) propertyChangeEvent.getNewValue());
    }

    private void setLocalDocument(LocalDocument newLocalDocument) {
        if (this.localDocument != null) {
            this.localDocument.removeObserver(this.observer);
        }
        this.localDocument = newLocalDocument;
        if (newLocalDocument != null) {
            this.localDocument.addObserver(this.observer);
        }
        try {
            this.swingDocument.removeProgrammatically(0, this.swingDocument.getLength());
        } catch (BadLocationException e) {
            ExceptionHandler.getExceptionHandler().handle(e, Level.WARNING, "Could not clear document. Continuing without properly clearing", this.getClass());
        }
    }

    private void leaveEditor() {
        String documentId = this.sessionStorage.getCurrentSharedDocument().getFuncId();
        try {
            this.sessionStorage.getLobby().leaveDocument(this.user, documentId);
        } catch (InvalidDocumentIdException.DocumentDoesNotExistException | UnknownUserException e) {
            throw new RuntimeException("Could not leave document. Continuing as usual.", e);
        }
        this.user.setDocument(null);
        this.window.switchToView(ViewProperties.MENU);
    }

    private void exportText() {
        JFileChooser fileChooser = new JFileChooser(System.getProperty("user.home") + "/Desktop");
        if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            try (PrintWriter output = new PrintWriter(FileHelper.parseFile(fileChooser.getSelectedFile()))) {
                output.println(this.editor.getText());
            } catch (FileNotFoundException e) {
                ExceptionHandler.getExceptionHandler().handle(e, "Failed to write Text into file", EditorPanel.class);
            }
        }
    }

    private void addChar(char value, int offset) {
        this.logger.log(Level.INFO, String.format("Inserted Character %c at Position %d", value, offset));
        EventQueue.invokeLater(() -> {
            try {
                this.swingDocument.insertStringProgrammatically(offset - 1, Character.toString(value), null);
                System.out.println("Inserted character successfully");
            } catch (BadLocationException e) {
                ExceptionHandler.getExceptionHandler().handle(e, Level.SEVERE, "Error trying to insert character into receiver localDocument. Skipping this character, but continuing as usual.", EditorPanel.class);
            }
        });
    }

    private void removeChar(char value, int offset) {
        this.logger.log(Level.INFO, String.format("Removed Character %c at Position %d", value, offset - 1));
        EventQueue.invokeLater(() -> {
            try {
                this.swingDocument.removeProgrammatically(offset - 1, 1);
                System.out.println("Removed character successfully");
            } catch (BadLocationException e) {
                ExceptionHandler.getExceptionHandler().handle(e, Level.SEVERE, "Error trying to remove character from receiver localDocument. Skipping this character, but continuing as usual.", EditorPanel.class);
            }
        });
    }
}