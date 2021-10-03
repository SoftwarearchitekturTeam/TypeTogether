package de.hswhameln.typetogether.client.businesslogic;

import de.hswhameln.typetogether.client.gui.EditorPanel;
import de.hswhameln.typetogether.networking.LocalDocument;
import de.hswhameln.typetogether.networking.api.Document;
import de.hswhameln.typetogether.networking.api.User;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class ClientUser implements User {

    private final PropertyChangeSupport propertyChangeSupport;
    public static final String LOCAL_DOCUMENT = "localDocument";

    private final int userId;
    private final String name;
    private LocalDocument document;

    public ClientUser(String name) {
        this.propertyChangeSupport = new PropertyChangeSupport(this);
        this.name = name;
        this.userId = (int) (Math.random() * 100000 + 1);
    }

    @Override
    public int getId() {
        return this.userId;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Document getDocument() {
        return this.document;
    }

    public void setDocument(LocalDocument document) {
        LocalDocument old = this.document;
        this.document = document;
        this.propertyChangeSupport.firePropertyChange(LOCAL_DOCUMENT, old, this.document);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        this.propertyChangeSupport.addPropertyChangeListener(listener);
    }
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        this.propertyChangeSupport.removePropertyChangeListener(listener);
    }
}
