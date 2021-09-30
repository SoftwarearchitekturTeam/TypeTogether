package de.hswhameln.typetogether.client.businesslogic;

import de.hswhameln.typetogether.networking.api.User;
import de.hswhameln.typetogether.networking.api.Document;
import de.hswhameln.typetogether.networking.LocalDocument;

public class ClientUser implements User {
    
    private LocalDocument document;
    private final int userId;
    private final String name;

    public ClientUser(String name) {
        this.name = name;
        this.userId = (int)(Math.random() * 100000 + 1);
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
        this.document = document;
    }
}
