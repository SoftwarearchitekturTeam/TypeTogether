package de.hswhameln.typetogether.client.gui;

import static de.hswhameln.typetogether.client.gui.util.ButtonFactory.createLeftButton;
import static de.hswhameln.typetogether.client.gui.util.ButtonFactory.createRightButton;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import de.hswhameln.typetogether.client.runtime.ClientRuntime;

public class EditorPanel extends AbstractPanel {
    
    private JScrollPane editorPane;
    private JTextArea editor;
    private JButton leave;
    private JButton export;

    public EditorPanel(MainWindow window/*LocalDocument localDocument, LocalDocumentSender localDocumentSender*/) {
        super(window);
        this.editor = new JTextArea(5, 20);
        this.editor.setFont(ViewProperties.EDITOR_FONT);
        this.editorPane = new JScrollPane(this.editor);
        this.editorPane.setMaximumSize(ViewProperties.EDITOR_SIZE);
        this.editorPane.setBorder(BorderFactory.createMatteBorder(50, 0, 50, 0, ViewProperties.GREY_BUTTON_COLOR));
        this.leave = createRightButton("Verlassen", this::leaveEditor);
        this.export = createLeftButton("Exportieren", this::exportText);
        this.export.setEnabled(false);
        ClientRuntime runtime = this.window.getClientRuntime();
        this.editor.getDocument().addDocumentListener(new EditorListener(runtime.getLocalDocument(), runtime.getSender(), runtime.getUser()));

        this.editor.setText("Editor-Test");
        Component rigArea = Box.createRigidArea(new Dimension(20, 0));
        this.addComponents(editorPane, leave, rigArea, export);
    } 

    private void leaveEditor() {
        this.window.switchToView(ViewProperties.MENU);
    }

    private void exportText() {

    }
}