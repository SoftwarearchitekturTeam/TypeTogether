package de.hswhameln.typetogether.client;

import de.hswhameln.typetogether.client.runtime.ClientRuntime;

import java.util.Map;

public class ClientStarter {

    public static void main(String[] args) {
        ArgumentParser argumentParser = new ArgumentParser();
        Map<String, String> arguments = argumentParser.parse(args);

        ClientRuntime clientRuntime = new ClientRuntime(arguments);

    }
}