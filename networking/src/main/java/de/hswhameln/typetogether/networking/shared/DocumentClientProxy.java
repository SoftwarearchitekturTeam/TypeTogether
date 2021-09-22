package de.hswhameln.typetogether.networking.shared;

import java.net.Socket;

import de.hswhameln.typetogether.networking.api.Document;
import de.hswhameln.typetogether.networking.api.User;
import de.hswhameln.typetogether.networking.types.DocumentCharacter;

public class DocumentClientProxy extends AbstractClientProxy implements Document {

    public DocumentClientProxy(Socket socket) {
        super(socket);
    }

    @Override
    public void addChar(User author, DocumentCharacter character) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Not yet implemented");

    }

    @Override
    public void removeChar(User author, DocumentCharacter character) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Not yet implemented");

    }

    @Override
    public String getFuncId() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Not yet implemented");
    }

}
