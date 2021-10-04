package de.hswhameln.typetogether.client.gui;

import javax.swing.*;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;

import de.hswhameln.typetogether.client.businesslogic.ClientUser;
import de.hswhameln.typetogether.client.runtime.PropertyChangeManager;
import de.hswhameln.typetogether.client.runtime.SessionStorage;
import de.hswhameln.typetogether.client.runtime.commands.CommandInvoker;
import de.hswhameln.typetogether.networking.api.Document;


public class CommandPanel extends AbstractPanel {

    private JButton undo;
    private JButton redo;
    private JLabel username;
    private ClientUser user;
    
    private JLabel documentname;
    private Document sharedDocument;
    private JMenuBar menubar;
    private JPanel editorPanel;
    private CommandInvoker invoker;
    private final PropertyChangeManager propertyChangeManager;

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
        this.setUser(sessionStorage.getCurrentUser());
        this.propertyChangeManager = new PropertyChangeManager();
        sessionStorage.addPropertyChangeListener(this.propertyChangeManager);

        this.propertyChangeManager.onPropertyChange(SessionStorage.CURRENT_USER, this::currentUserChanged);
        this.propertyChangeManager.onPropertyChange(SessionStorage.CURRENT_SHARED_DOCUMENT, this::currentSharedDocumentChanged);


        username = new JLabel();
     
     documentname = new JLabel();

        this.menubar.add(undo);
        this.menubar.add(redo);
        this.menubar.add(Box.createHorizontalGlue());
        this.menubar.add(documentname);
        this.menubar.add(username);
        this.add(this.menubar, BorderLayout.NORTH);
        this.add(editorPanel, BorderLayout.CENTER);

   }

   public void changeClickableUndo(boolean enabled) {
       this.undo.setEnabled(enabled);
   }

    public void changeClickableRedo(boolean enabled) {
        this.redo.setEnabled(enabled);
    }
    private void currentSharedDocumentChanged(PropertyChangeEvent propertyChangeEvent) {
     this.setDocumentname ( (Document) propertyChangeEvent.getNewValue());
 }
 private void setDocumentname(Document newValue) {
      this.sharedDocument = newValue;
      this.documentname.setText(this.sharedDocument.getFuncId());
}

private void currentUserChanged(PropertyChangeEvent propertyChangeEvent) {
     setUser((ClientUser) propertyChangeEvent.getNewValue());
 }

 private void setUser(ClientUser newUser) {
     if (this.user != null) {
         this.user.removePropertyChangeListener(this.propertyChangeManager);
     }
     this.user = newUser;
     if (this.user != null) {
         this.user.addPropertyChangeListener(this.propertyChangeManager);
         this.username.setText(this.user.getName());
     }
 }
}
