package de.hswhameln.typetogether.testclient;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class ConsoleClient {

    private final Scanner sc;
    private final Socket socket;
    private final BufferedReader in;
    private final PrintWriter out;

    public ConsoleClient() throws IOException {
        this.sc = new Scanner(System.in);
        this.socket = createSocket();

        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);

    }

    public void start() throws IOException {
        boolean running = true;
        System.out.println("Press any input to send to the server or /quit to stop.");
        while (running && socket.isConnected()) {
            String input = sc.nextLine();
            if ("".equals(input)) {
                System.out.println("Server sent: " + in.readLine());
            } else if ("/quit".equals(input)) {
                running = false;
            } else {
                out.println(input);
                System.out.println("Server replied: " + in.readLine());
            }
        }

        this.cleanUp();
    }

    public void cleanUp() throws IOException {
        sc.close();
        socket.close();
    }

    private Socket createSocket() throws IOException {
        System.out.print("Enter network address (localhost):");
        String target = sc.nextLine();
        if (target == null || target.isBlank()) {
            target = "localhost";
        }
        System.out.print("Enter port (12557):");
        String port = sc.nextLine();
        if (port == null || port.isBlank()) {
            port = "12557";
        }
        return new Socket(target, Integer.parseInt(port));
    }

}
