package de.hswhameln.typetogether.client.gui;

import static de.hswhameln.typetogether.client.gui.util.ButtonFactory.createLeftButton;
import static de.hswhameln.typetogether.client.gui.util.ButtonFactory.createRightButton;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.text.BadLocationException;

import de.hswhameln.typetogether.client.businesslogic.DocumentObserver;
import de.hswhameln.typetogether.client.gui.util.FileHelper;
import de.hswhameln.typetogether.client.runtime.ClientRuntime;
import de.hswhameln.typetogether.networking.util.ExceptionHandler;

public class EditorPanel extends AbstractPanel {

    private Logger logger = Logger.getLogger(this.getClass().getName());
    private DocumentObserver observer;

    private JScrollPane editorPane;
    private JTextArea editor;
    private JButton leave;
    private JButton export;

    private CustomSwingDocument swingDocument;

    public EditorPanel(MainWindow window/*LocalDocument localDocument, LocalDocumentSender localDocumentSender*/) {
        super(window);
        this.observer = new DocumentObserver(this::addChar, this::removeChar);
        this.swingDocument = new CustomSwingDocument();
        this.editor = new JTextArea(this.swingDocument, "", 5, 20);
        this.editor.setFont(ViewProperties.EDITOR_FONT);
        this.editorPane = new JScrollPane(this.editor);
        this.editorPane.setMaximumSize(ViewProperties.EDITOR_SIZE);
        this.editorPane.setBorder(BorderFactory.createEmptyBorder(50, 0, 50, 0));
        this.leave = createRightButton("Verlassen", this::leaveEditor);
        this.export = createLeftButton("Exportieren", this::exportText);

        this.editor.setText("");
        Component rigArea = Box.createRigidArea(new Dimension(20, 0));
        this.addComponents(editorPane, leave, rigArea, export);
    }

    private void leaveEditor() {
        this.window.switchToView(ViewProperties.MENU);
    }

    private void exportText() {
        JFileChooser fileChooser = new JFileChooser(System.getProperty("user.home") + "/Desktop");
        if(fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            try(PrintWriter output = new PrintWriter(FileHelper.parseFile(fileChooser.getSelectedFile()))) {
                output.println(this.editor.getText());
            } catch (FileNotFoundException e) {
                ExceptionHandler.getExceptionHandler().handle(e, "Failed to write Text into file", EditorPanel.class);
            }
        }
    }

    @Override
    public void initialize() {
        ClientRuntime runtime = this.window.getClientRuntime();
        this.swingDocument.addDocumentListener(new EditorListener(runtime.getLocalDocument(), runtime.getSender(), runtime.getUser()));

        runtime.getLocalDocument().addObserver(this.observer);
    }

    private void addChar(char value, int offset) {
        this.logger.log(Level.INFO, String.format("Inserted Character %c at Position %d", value, offset));
        EventQueue.invokeLater(() -> {
            try {
                this.swingDocument.insertStringProgrammatically(offset - 1, Character.toString(value), null);
                System.out.println("Inserted character successfully");
            } catch (BadLocationException e) {
                ExceptionHandler.getExceptionHandler().handle(e, "Error trying to insert character into receiver localDocument", EditorPanel.class);
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
                ExceptionHandler.getExceptionHandler().handle(e, "Error trying to remove character into receiver localDocument", EditorPanel.class);
            }
        });

    }
}