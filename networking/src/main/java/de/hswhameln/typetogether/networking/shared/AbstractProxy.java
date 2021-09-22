package de.hswhameln.typetogether.networking.shared;

import de.hswhameln.typetogether.networking.proxy.ResponseCodes;

import java.io.*;
import java.net.Socket;
import java.util.logging.Logger;

public class AbstractProxy {
    protected Logger logger = Logger.getLogger(this.getClass().getName());
    protected Socket socket;

    protected BufferedReader in;
    protected PrintWriter out;

    public AbstractProxy(Socket socket) {
        this.socket = socket;
    }


    protected void openStreams() throws IOException {
        this.in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
        this.out = new PrintWriter(new OutputStreamWriter(this.socket.getOutputStream()));
    }

    protected void success() {
        this.out.println(ResponseCodes.SUCCESS);
    }

    protected void error(String responseCode, String message) {
        this.out.println(responseCode);
        this.out.println(message);
    }

}
