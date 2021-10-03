package de.hswhameln.typetogether.client.gui;

import javax.swing.JButton;
import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Color;

import de.hswhameln.typetogether.client.runtime.SessionStorage;


public class CommandPanel extends AbstractPanel {

    private JButton undo;
    private JButton redo;
    private JPanel editorPanel;

   public CommandPanel (MainWindow window, SessionStorage sessionStorage) {
        super(window, sessionStorage);
      
        this.setLayout(new BorderLayout());
        this.setSize(ViewProperties.DEFAULT_WIDTH, ViewProperties.DEFAULT_HEIGHT);
        this.setPreferredSize(new Dimension(ViewProperties.DEFAULT_WIDTH, ViewProperties.DEFAULT_HEIGHT));
        this.setVisible(true);
        this.setBackground(Color.WHITE);
        undo = new JButton();
        redo = new JButton();
        editorPanel = new EditorPanel(window, sessionStorage);
        undo.addActionListener(a-> this.undo());
        redo.addActionListener(a-> this.redo());
        this.add(undo, BorderLayout.NORTH);
        this.add(redo, BorderLayout.NORTH);
        this.add(editorPanel, BorderLayout.CENTER);

   }
   private void undo( ){

   }
   private void redo(){
       
   }
}
