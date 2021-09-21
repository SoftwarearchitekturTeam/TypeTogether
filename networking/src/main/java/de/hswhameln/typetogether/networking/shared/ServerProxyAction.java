package de.hswhameln.typetogether.networking.shared;

public class ServerProxyAction {
    private final Action action;
    private final String name;

    public ServerProxyAction(Action action, String name) {
        this.action = action;
        this.name = name;
    }

    public Action getAction() {
        return action;
    }

    public String getName() {
        return name;
    }
}
