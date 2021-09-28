package de.hswhameln.typetogether.testclient;

import java.util.Scanner;
import java.util.logging.Level;

import de.hswhameln.typetogether.networking.util.LoggerUtils;

public class ConsoleClientStarter {
    public static void main(String[] args) throws Exception {
        LoggerUtils.setLogLevel(Level.FINEST);

        Scanner sc = new Scanner(System.in);
        System.out.println("Press 1 to start manual client or 2 to start LobbyClientProxy assisted client");
        String in = sc.nextLine();
        if ("1".equals(in)) {
            System.out.println("Starting manual client");
            new ConsoleClient(sc).start();
        } else if ("2".equals(in)) {
            System.out.println("Starting assisted client");
            new ConsoleClientWithLobbyClientProxy(sc).start();
        }
        System.out.println("Unknown input, try again soon.");
    }
}
