package de.hswhameln.typetogether.server.proxy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

import de.hswhameln.typetogether.networking.api.Document;
import de.hswhameln.typetogether.networking.api.User;
import de.hswhameln.typetogether.networking.types.DocumentCharacter;

public class DocumentClientProxy implements Document {

    private Socket socket;
    private BufferedReader reader;
    private PrintWriter writer;

    public DocumentClientProxy(String hostname, int port) {
        try {
            this.socket = new Socket(hostname, port);

            if (this.openInputStream() == false || this.openOutputStream() == false) {
                return;
            }
        } catch (Throwable t) {

            //TODO: Logging // System.out.println("[ERROR]: beim öffnen des Sockets ist folgender Fehelr aufgetreten:");
            t.printStackTrace();
        }
    }

    private boolean openInputStream () {
        try {
            this.reader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            return true;
        } catch (IOException e) {
            //TODO: Logging // System.out.println("[ERROR]: beim öffnen des Input-Streams ist folgender Fehelr aufgetreten:");
            e.printStackTrace();
            return false;
        }
    }

    private boolean openOutputStream () {
        try {
            this.writer= new PrintWriter(new OutputStreamWriter(this.socket.getOutputStream()), true);
            return true;
        } catch (IOException e) {
            //TODO: Logging // System.out.println("[ERROR]: beim öffnen des Output-Streams ist folgender Fehelr aufgetreten:");
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void writeChar(User author, DocumentCharacter character) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void deleteChar(User author, DocumentCharacter character) {
        // TODO Auto-generated method stub
        
    }
    
}
