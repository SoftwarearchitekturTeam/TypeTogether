package de.hswhameln.typetogether.networking.shared;

public class ServerProxyAction {
    private final String name;
    private final Action action;

    public ServerProxyAction(String name, Action action) {
        this.action = action;
        this.name = name;
    }

    public Action getAction() {
        return action;
    }

    public String getName() {
        return name;
    }

    public static ServerProxyAction of(String name, Action action) {
        return new ServerProxyAction(name, action);
    }
}
