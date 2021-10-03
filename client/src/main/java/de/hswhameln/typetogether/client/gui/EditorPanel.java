package de.hswhameln.typetogether.client.gui;

import de.hswhameln.typetogether.client.gui.util.FileHelper;
import de.hswhameln.typetogether.client.runtime.ClientRuntime;
import de.hswhameln.typetogether.networking.DocumentObserver;
import de.hswhameln.typetogether.networking.util.ExceptionHandler;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import java.awt.*;
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

    public EditorPanel(MainWindow window/*LocalDocument localDocument, LocalDocumentSender localDocumentSender*/) {
        super(window);
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
    }

    private void leaveEditor() {
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