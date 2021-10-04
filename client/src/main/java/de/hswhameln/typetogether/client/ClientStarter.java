package de.hswhameln.typetogether.client;

import java.util.Map;
import java.util.logging.Level;

import de.hswhameln.typetogether.client.gui.MainWindow;
import de.hswhameln.typetogether.client.runtime.ClientRuntime;
import de.hswhameln.typetogether.client.runtime.SessionStorage;
import de.hswhameln.typetogether.networking.util.LoggerUtils;

public class ClientStarter {

    public static void main(String[] args) {
        LoggerUtils.setLogLevel(Level.parse(System.getProperty("loglevel")));
        System.out.println("_______   _____ ___ _____ __   __ ___ _____ _  _ ___ ___");  
        System.out.println("|_  _\\ `v' / _,\\ __|_   _/__\\ / _] __|_   _| || | __| _ \\");
        System.out.println(" | |  `. .'| v_/ _|  | || \\/ | [/\\ _|  | | | >< | _|| v /"); 
        System.out.println(" |_|   !_! |_| |___| |_| \\__/ \\__/___| |_| |_||_|___|_|_\\");

        ClientRuntime clientRuntime = new ClientRuntime();
        SessionStorage sessionStorage = new SessionStorage(clientRuntime.createLobby());
        MainWindow window = new MainWindow(sessionStorage);
        window.setVisible(true);
    }
}