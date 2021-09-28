package de.hswhameln.typetogether.client.gui;

import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class EditorPanel extends AbstractPanel {
    
    private JTextArea editor;

    public EditorPanel() {
        this.editor = new JTextArea();
        this.editor.getDocument().addDocumentListener(new EditorListener(null, null));
    }
}