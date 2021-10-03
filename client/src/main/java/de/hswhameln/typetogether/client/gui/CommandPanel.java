package de.hswhameln.typetogether.client.gui;

import javax.swing.*;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Color;

import de.hswhameln.typetogether.client.runtime.SessionStorage;


public class CommandPanel extends AbstractPanel {

    private JButton undo;
    private JButton redo;
    private JMenuBar menubar;
    private JPanel editorPanel;

   public CommandPanel (MainWindow window, SessionStorage sessionStorage) {
        super(window, sessionStorage);
      
        this.setLayout(new BorderLayout());
        this.setSize(ViewProperties.DEFAULT_WIDTH, ViewProperties.DEFAULT_HEIGHT);
        this.setPreferredSize(new Dimension(ViewProperties.DEFAULT_WIDTH, ViewProperties.DEFAULT_HEIGHT));
        this.setVisible(true);
        this.setBackground(Color.WHITE);

        this.menubar = new JMenuBar();
        undo = new JButton("Undo");
        undo.setForeground(ViewProperties.FONT_COLOR);
        undo.setVisible(true);
        redo = new JButton("Redo");
        redo.setForeground(ViewProperties.FONT_COLOR);
        redo.setVisible(true);
        editorPanel = new EditorPanel(window, sessionStorage);
        undo.addActionListener(a-> this.undo());
        redo.addActionListener(a-> this.redo());
        this.menubar.add(undo);
        this.menubar.add(redo);
        this.add(this.menubar, BorderLayout.NORTH);
        this.add(editorPanel, BorderLayout.CENTER);

   }
   private void undo( ){

   }
   private void redo(){
       
   }
}
