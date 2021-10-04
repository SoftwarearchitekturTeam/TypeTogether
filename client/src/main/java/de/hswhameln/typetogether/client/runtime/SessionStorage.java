package de.hswhameln.typetogether.client.runtime;

import de.hswhameln.typetogether.client.businesslogic.ClientUser;
import de.hswhameln.typetogether.client.gui.CommandPanel;
import de.hswhameln.typetogether.client.runtime.commands.CommandInvoker;
import de.hswhameln.typetogether.networking.api.Document;
import de.hswhameln.typetogether.networking.api.Lobby;
import de.hswhameln.typetogether.networking.api.User;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class SessionStorage {
    private final Lobby lobby;
    private ClientUser currentUser;
    private Document currentSharedDocument;
    private CommandInvoker invoker;

    // Descriptors
    public static final String CURRENT_USER = "currentUser";
    public static final String CURRENT_SHARED_DOCUMENT = "currentSharedDocument";
    public static final String CURRENT_LOCAL_DOCUMENT = "currentLocalDocument";

    private final PropertyChangeSupport propertyChangeSupport;

    public SessionStorage(Lobby lobby) {
        this.lobby = lobby;
        this.propertyChangeSupport = new PropertyChangeSupport(this);
        this.invoker = new CommandInvoker();
    }

    public void setCurrentUser(ClientUser currentUser) {
        User old = this.currentUser;
        this.currentUser = currentUser;
        this.propertyChangeSupport.firePropertyChange(CURRENT_USER, old, this.currentUser);
    }

    public void setCurrentSharedDocument(Document currentSharedDocument) {
        Document old = this.currentSharedDocument;
        this.currentSharedDocument = currentSharedDocument;
        this.propertyChangeSupport.firePropertyChange(CURRENT_SHARED_DOCUMENT, old, this.currentSharedDocument);
    }

    public ClientUser getCurrentUser() {
        return currentUser;
    }

    public void addPropertyChangeListener(PropertyChangeListener propertyChangeListener) {
        this.propertyChangeSupport.addPropertyChangeListener(propertyChangeListener);
    }

    public Lobby getLobby() {
        return this.lobby;
    }

    public CommandInvoker getCommandInvoker() {
        return this.invoker;
    }

    public Document getCurrentSharedDocument() {
        return currentSharedDocument;
    }

}
