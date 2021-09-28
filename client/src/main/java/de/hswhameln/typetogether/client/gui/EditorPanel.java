package de.hswhameln.typetogether.client.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Cursor;

import static de.hswhameln.typetogether.client.gui.util.ButtonFactory.createButton;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import de.hswhameln.typetogether.client.businesslogic.LocalDocument;
import de.hswhameln.typetogether.client.businesslogic.LocalDocumentSender;

public class EditorPanel extends AbstractPanel {
    
    private JScrollPane editorPane;
    private JTextArea editor;
    private JButton leave;

    public EditorPanel(/*LocalDocument localDocument, LocalDocumentSender localDocumentSender*/) {
        this.editor = new JTextArea(5, 20);
        this.editor.setFont(ViewProperties.EDITOR_FONT);
        this.editorPane = new JScrollPane(this.editor);
        this.editorPane.setMaximumSize(ViewProperties.EDITOR_SIZE);
        this.editorPane.setBorder(BorderFactory.createEmptyBorder(50, 0, 50, 0));
        this.leave = createButton("Verlassen");
        this.editor.getDocument().addDocumentListener(new EditorListener(null, null));

        this.editor.setText("Editor-Test");

        this.addComponents(editorPane, leave);
    } 
}