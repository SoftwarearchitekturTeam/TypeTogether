package de.hswhameln.typetogether.client.runtime;

import de.hswhameln.typetogether.networking.api.Document;
import de.hswhameln.typetogether.networking.util.ObjectDestructor;

import java.beans.PropertyChangeEvent;

public class SessionStorageCleaner {

    private final PropertyChangeManager propertyChangeManager = new PropertyChangeManager();

    public SessionStorageCleaner(SessionStorage sessionStorage) {

        sessionStorage.addPropertyChangeListener(this.propertyChangeManager);
    }

    public void start() {
        propertyChangeManager.onPropertyChange(SessionStorage.CURRENT_SHARED_DOCUMENT, this::currentSharedDocumentChanged);
    }

    private void currentSharedDocumentChanged(PropertyChangeEvent propertyChangeEvent) {
        Document sharedDocument = (Document) propertyChangeEvent.getOldValue();
        if (sharedDocument != null) {
            ObjectDestructor.destroy(sharedDocument);
        }
    }

}
