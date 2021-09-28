package de.hswhameln.typetogether.testclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.function.Function;

import de.hswhameln.typetogether.networking.api.Document;
import de.hswhameln.typetogether.networking.api.User;
import de.hswhameln.typetogether.networking.shared.AbstractServerProxy;
import de.hswhameln.typetogether.networking.shared.UserServerProxy;

public class ConsoleClient {

    private final Scanner sc;
    private final Socket socket;
    private final BufferedReader in;
    private final PrintWriter out;

    private final User user;
    private final Document document;
    private final ConsoleClientHelper consoleClientHelper;

    public ConsoleClient(Scanner sc) throws IOException {
        this.sc = sc;
        this.consoleClientHelper = new ConsoleClientHelper(this.sc);
        this.socket = this.consoleClientHelper.createSocket();

        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);

        this.document = this.consoleClientHelper.createDocument();
        this.user = this.consoleClientHelper.createUser();

    }

    public void start() throws IOException {
        boolean running = true;
        System.out.println("Press any input to send to the server or /quit to stop.");
        System.out.println("or /user <port> to start a client-side user server.");
        while (running && socket.isConnected()) {
            String input = sc.nextLine();
            if ("".equals(input)) {
                System.out.println("Server sent: " + in.readLine());
            } else if ("/quit".equals(input)) {
                running = false;
            } else if (input.startsWith("/user")) {
                int port = Integer.parseInt(input.split(" ")[1]);
                new Thread(() -> {
                    startServer(port, socket1 -> new UserServerProxy(socket1, user));
                }).start();
            } else {
                out.println(input);
                System.out.println("Server replied: " + in.readLine());
            }
        }

        this.cleanUp();
    }

    private <T> void startServer(int port, Function<Socket, AbstractServerProxy> aNew) {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            Socket clientSocket = serverSocket.accept();
            System.out.println("new connection established");
            new Thread(aNew.apply(clientSocket)).start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void cleanUp() throws IOException {
        sc.close();
        socket.close();
    }

}
