package de.hswhameln.typetogether.server.proxy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

import de.hswhameln.typetogether.networking.api.Document;
import de.hswhameln.typetogether.networking.api.User;
import de.hswhameln.typetogether.networking.shared.AbstractClientProxy;
import de.hswhameln.typetogether.networking.types.DocumentCharacter;

public class DocumentClientProxy extends AbstractClientProxy implements Document {

    public DocumentClientProxy(String hostname, int port) throws IOException {
        super(hostname, port);
    }

    @Override
    public void addChar(User author, DocumentCharacter character) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void removeChar(User author, DocumentCharacter character) {
        // TODO Auto-generated method stub
        
    }
    
}
