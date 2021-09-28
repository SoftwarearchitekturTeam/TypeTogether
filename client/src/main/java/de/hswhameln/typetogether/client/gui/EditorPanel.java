package de.hswhameln.typetogether.client.gui;

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JTextArea;

public class EditorPanel extends AbstractPanel {
    
    private JTextArea editor;
    private JButton leave;

    public EditorPanel() {
        this.editor = new JTextArea();
        this.leave = new JButton();
        this.editor.getDocument().addDocumentListener(new EditorListener(null, null));
        this.editor.setSize(ViewProperties.DEFAULT_WIDTH / 2, ViewProperties.DEFAULT_HEIGHT / 2);

        this.setBackground(Color.CYAN);
        this.editor.setText("Editor-Test");

        this.addComponents(editor, leave);
    }

    
}