package de.hswhameln.typetogether.client.gui;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class EditorPanel extends AbstractPanel {
    
    private JScrollPane editorPane;
    private JTextArea editor;
    private JButton leave;

    public EditorPanel() {
        this.editor = new JTextArea(5, 20);
        this.editor.setFont(ViewProperties.EDITOR_FONT);
        this.editorPane = new JScrollPane(this.editor);
        this.editorPane.setMaximumSize(ViewProperties.EDITOR_SIZE);
        this.editorPane.setBorder(BorderFactory.createEmptyBorder(50, 0, 50, 0));
        this.leave = new JButton("Verlassen");
        this.editor.getDocument().addDocumentListener(new EditorListener(null, null));

        this.setBackground(Color.CYAN);
        this.editor.setText("Editor-Test");

        this.addComponents(editorPane, leave);
    } 
}