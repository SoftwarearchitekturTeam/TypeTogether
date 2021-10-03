package de.hswhameln.typetogether.client.gui;

import javax.swing.*;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import de.hswhameln.typetogether.client.runtime.SessionStorage;
import de.hswhameln.typetogether.client.runtime.commands.base.CommandInvoker;


public class CommandPanel extends AbstractPanel {

    private JButton undo;
    private JButton redo;
    private JMenuBar menubar;
    private JPanel editorPanel;
    private CommandInvoker invoker;

   public CommandPanel (MainWindow window, SessionStorage sessionStorage) {
        super(window, sessionStorage);
        this.invoker = sessionStorage.getCommandInvoker();
      
        this.setLayout(new BorderLayout());
        this.setSize(ViewProperties.DEFAULT_WIDTH, ViewProperties.DEFAULT_HEIGHT);
        this.setPreferredSize(new Dimension(ViewProperties.DEFAULT_WIDTH, ViewProperties.DEFAULT_HEIGHT));
        this.setVisible(true);
        this.setBackground(Color.WHITE);

        this.menubar = new JMenuBar();
        undo = new JButton("Undo");
        undo.setForeground(ViewProperties.FONT_COLOR);
        undo.setVisible(true);
        undo.setEnabled(false);
        redo = new JButton("Redo");
        redo.setForeground(ViewProperties.FONT_COLOR);
        redo.setVisible(true);
        redo.setEnabled(false);
        editorPanel = new EditorPanel(window, sessionStorage);

        undo.addActionListener(a-> this.invoker.undo());
        redo.addActionListener(a-> this.invoker.redo());

        this.menubar.add(undo);
        this.menubar.add(redo);
        this.add(this.menubar, BorderLayout.NORTH);
        this.add(editorPanel, BorderLayout.CENTER);

   }

   public void changeClickableUndo(boolean enabled) {
       this.undo.setEnabled(enabled);
   }

    public void changeClickableRedo(boolean enabled) {
        this.redo.setEnabled(enabled);
    }
}
