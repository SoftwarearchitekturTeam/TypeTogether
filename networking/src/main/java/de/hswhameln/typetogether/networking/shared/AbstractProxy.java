package de.hswhameln.typetogether.networking.shared;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Logger;

import de.hswhameln.typetogether.networking.util.ExceptionHandler;
import de.hswhameln.typetogether.networking.util.IOUtils;

public class AbstractProxy {
    protected Logger logger = Logger.getLogger(this.getClass().getName());
    protected ExceptionHandler exceptionHandler = ExceptionHandler.getExceptionHandler();
    protected Socket socket;

    protected BufferedReader in;
    protected PrintWriter out;

    public AbstractProxy(Socket socket) {
        this.socket = socket;
        try {
            this.openStreams();
        } catch (IOException e) {
            exceptionHandler.handle(e, "Unexpected initialization error", this.getClass());
        }
    }

    private void openStreams() throws IOException {
        this.in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
        this.out = new PrintWriter(new OutputStreamWriter(this.socket.getOutputStream()), true);
    }

    protected void success() {
        IOUtils.success(this.out);
    }

    protected void error(String responseCode, String message) {
        IOUtils.error(responseCode, message, this.out);
    }

}
