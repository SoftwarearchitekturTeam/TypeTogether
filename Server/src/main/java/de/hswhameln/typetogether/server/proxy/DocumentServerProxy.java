package de.hswhameln.typetogether.server.proxy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import de.hswhameln.typetogether.networking.api.Document;

//TODO: Logging
public class DocumentServerProxy implements Runnable {

    private Socket socket;
    //private IChatProvider chatProvider;
    private BufferedReader reader;
    private PrintWriter writer;
    private Map<Integer, DocumentClientProxy> documents = new HashMap<>();
    
    public DocumentServerProxy(Socket socket/*, provider*/) {
        this.socket = socket;
        //this.chatProvider = chatProvider;
    }

    private Document resolveDocument () throws Exception {

        this.writer.println("Please provide Document-Com-ID");
        String sCommunicationId = this.reader.readLine();
        int communicationId = Integer.valueOf (sCommunicationId);

        if (this.documents.containsKey(communicationId) == false) {

            this.writer.println("Please provide Document-Port");

            String sPort = this.reader.readLine();

            int port = Integer.valueOf(sPort);
            DocumentClientProxy clientProxy = new DocumentClientProxy(this.socket.getInetAddress().getHostAddress(), port);

            this.documents.put(communicationId, clientProxy);
        } else {
            this.writer.println("Could not resolve Document: Document-Com-ID already in use");
        }
        return this.documents.get(communicationId);
    }

    private boolean openInputStream () {
        try {
            this.reader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            return true;
        } catch (IOException e) {
            System.err.println("[ERROR]: Could not open InputStream:");
            e.printStackTrace();
            return false;
        }
    }

    private boolean openOutputStream () {
        try {
            this.writer = new PrintWriter(new OutputStreamWriter(this.socket.getOutputStream()), true);
            return true;
        } catch (IOException e) {
            System.err.println("[ERROR]: Could not open OutputStream:");
            e.printStackTrace();
            return false;
        }
    }


    @Override
    public void run() {
        try {
            // Öffne den Reader/Writer:
            if (this.openInputStream() == false || this.openOutputStream() == false) {
                return;
            }

            //TODO: Protokoll
            // this.writer.println("Buenas Dias");
            // this.writer.println("Available Commands:");
            // this.writer.println("0 - kill the connection");
            // this.writer.println("1 - joinChat");
            // this.writer.println("2 - leaveChat");
            // this.writer.println("3 - sendMessage");
            // this.writer.println("----");

            String line;
            while ((line = this.reader.readLine()) != null) {
                System.out.println("[INFORMATION]: Client sent: " + line);
                switch (line) {
                    case "0":
                        System.out.println("[INFORMATION]: Client is adding a char");
                        this.addChar();
                        return;
                    case "1":
                        System.out.println("[INFORMATION]: Client is removing a char");
                        this.removeChar();
                        break;
                    default:
                        throw new Exception("[PROTOCOL ERROR]: Unsupported document operation");
                }
            }
        } catch (Exception e) {
            System.err.println("[ERROR]: Could not read InputStream:");
            e.printStackTrace();
        }
    }

    private void addChar() throws Exception {
        Document document = this.resolveDocument();

        try {
            // TODO: Provider this.documentProvider.addChar(document);
            this.writer.println("200");
        } catch (Exception e) {
            this.writer.println("500");
            this.writer.println("[ERROR]: Could not add char: " + e.getMessage());
        }
    }

    private void removeChar() throws Exception {
        Document document = this.resolveDocument();

        try {
            // TODO: Provider this.documentProvider.removeChar(document);
            this.writer.println("200");
        } catch (Exception e) {
            this.writer.println("500");
            this.writer.println("[ERROR]: Could not remove char: " + e.getMessage());
        }
    }
    
}
