package de.hswhameln.typetogether.client;

import de.hswhameln.typetogether.client.gui.MainWindow;
import de.hswhameln.typetogether.client.runtime.ClientRuntime;
import de.hswhameln.typetogether.networking.util.ArgumentParser;
import de.hswhameln.typetogether.networking.util.LoggerUtils;

import java.util.Map;
import java.util.logging.Level;

public class ClientStarter {

    public static void main(String[] args) {
        Map<String, String> arguments = ArgumentParser.parse(args);
        //LoggerUtils.setLogLevel(Level.FINEST);

        //ClientRuntime clientRuntime = new ClientRuntime(arguments);
        MainWindow window = new MainWindow();
        window.setVisible(true);
    }
}