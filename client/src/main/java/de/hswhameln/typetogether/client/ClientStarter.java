package de.hswhameln.typetogether.client;

import java.util.Map;

import de.hswhameln.typetogether.client.gui.MainWindow;
import de.hswhameln.typetogether.client.runtime.ClientRuntime;
import de.hswhameln.typetogether.networking.util.ArgumentParser;

public class ClientStarter {

    public static void main(String[] args) {
        Map<String, String> arguments = ArgumentParser.parse(args);
        //LoggerUtils.setLogLevel(Level.FINEST);

        ClientRuntime clientRuntime = new ClientRuntime(arguments);
        MainWindow window = new MainWindow(clientRuntime);
        window.setVisible(true);
    }
}