package de.hswhameln.typetogether.server.proxy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import de.hswhameln.typetogether.networking.api.User;

//TODO: Logging
public class DocumentServerProxy implements Runnable {

    private Socket socket;
    //private IChatProvider chatProvider;
    private BufferedReader reader;
    private PrintWriter writer;
    private Map<Integer, User> users = new HashMap<>();
    
    public DocumentServerProxy(Socket socket/*, provider*/) {
        this.socket = socket;
        //this.chatProvider = chatProvider;
    }

    private User resolveUser () throws Exception {

        this.writer.println("Please provide User-Com-ID");
        String sCommunicationId = this.reader.readLine();
        int communicationId = Integer.valueOf (sCommunicationId);

        if (this.users.containsKey(communicationId) == false) {

            this.writer.println("Please provide User-Port");

            String sPort = this.reader.readLine();

            int port = Integer.valueOf(sPort);
            // TODO: Implement UserClientProxy User user = new UserClientProxy(this.socket.getInetAddress().getHostAddress(), port);

            // this.users.put(communicationId, user);
        } else {
            this.writer.println("Could not resolve User: User-Com-ID already in use");
        }
        return this.users.get(communicationId);
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

            // String line;
            // while ((line = this.reader.readLine()) != null) {
            //     System.out.println("[INFORMATION]: client sent: " + line);
            //     switch (line) {
            //         case "0":
            //             this.pleaseKill ();
            //             System.out.println("[INFORMATION]: client closed connection");
            //             return;
            //         case "1":
            //             this.pleaseJoin ();
            //             break;
            //         case "2":
            //             this.pleaseLeave ();
            //             break;
            //         case "3":
            //             this.pleaseSend ();
            //             break;
            //         default:
            //             throw new Exception("Unsupported operation");
            //     }
            // }
        } catch (Exception e) {
            System.err.println("[ERROR]: Could not read InputStream:");
            e.printStackTrace();
        }
    }
    
}
