package de.hswhameln.typetogether.networking.util;

import java.util.Collection;
import java.util.HashSet;

public class ShutdownHelper {
    private static final Collection<Runnable> hooks = new HashSet<>();

    public static void initialize() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            hooks.forEach(Runnable::run);
            ObjectDestructor.destroyAll();
        }));
    }

    /**
     * Add a shutdown hook that will be called before all connections are closed
     */
    public static void addShutdownHook(Runnable hook) {
        hooks.add(hook);
    }
}
