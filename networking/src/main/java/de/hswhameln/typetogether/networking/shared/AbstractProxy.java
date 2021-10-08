package de.hswhameln.typetogether.networking.shared;

import de.hswhameln.typetogether.networking.util.ExceptionHandler;
import de.hswhameln.typetogether.networking.util.IOUtils;
import de.hswhameln.typetogether.networking.util.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Logger;

public class AbstractProxy {
    protected final Logger logger = LoggerFactory.getLogger(this);
    protected final ExceptionHandler exceptionHandler = ExceptionHandler.getExceptionHandler();
    protected final Socket socket;

    protected BufferedReader in;
    protected PrintWriter out;

    public AbstractProxy(Socket socket) throws IOException {
        this.socket = socket;
        this.openStreams();
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
