package de.hswhameln.typetogether.client;

import java.util.Map;

import de.hswhameln.typetogether.client.gui.MainWindow;
import de.hswhameln.typetogether.client.runtime.ClientRuntime;
import de.hswhameln.typetogether.networking.util.ArgumentParser;

public class ClientStarter {

    public static void main(String[] args) {
        Map<String, String> arguments = ArgumentParser.parse(args);
        //LoggerUtils.setLogLevel(Level.FINEST);
        System.out.println("_______   _____ ___ _____ __   __ ___ _____ _  _ ___ ___");  
        System.out.println("|_  _\\ `v' / _,\\ __|_   _/__\\ / _] __|_   _| || | __| _ \\");
        System.out.println(" | |  `. .'| v_/ _|  | || \\/ | [/\\ _|  | | | >< | _|| v /"); 
        System.out.println(" |_|   !_! |_| |___| |_| \\__/ \\__/___| |_| |_||_|___|_|_\\");

        ClientRuntime clientRuntime = new ClientRuntime(arguments);
        MainWindow window = new MainWindow(clientRuntime);
        window.setVisible(true);
    }
}