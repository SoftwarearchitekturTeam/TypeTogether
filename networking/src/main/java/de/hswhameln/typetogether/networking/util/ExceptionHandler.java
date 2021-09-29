package de.hswhameln.typetogether.networking.util;

import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ExceptionHandler {
    private static Optional<ExceptionHandler> handler = Optional.empty();
    private Logger logger = Logger.getLogger(this.getClass().getName());

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
        logger.log(Level.WARNING, String.format("Class %s has encountered a problem: %s orginal error", clazz.getName(), msg), e);
    }
}
