package de.hswhameln.typetogether.networking.util;

import java.util.Optional;
import java.util.logging.Logger;

public class ExceptionHandler {
    private static Optional<ExceptionHandler> handler = Optional.empty();
    private Logger logger;

    private ExceptionHandler() {

    }

    public static ExceptionHandler getExceptionHandler() {
        if (handler.isPresent()) {
            return handler.get();
        }
        return createInstance();
    }

    private static ExceptionHandler createInstance() {
        handler = Optional.of(new ExceptionHandler());
        return handler.get();
    }

    public void handle(Exception e, String msg, Class clazz) {
        logger.warning(String.format("Class %s has encountered a problem: %s orginal error: %s", clazz.getName(), msg, e.getMessage()));
    }
}
