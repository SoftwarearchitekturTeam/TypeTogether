package de.hswhameln.typetogether.networking.util;

import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ExceptionHandler {
    private static ExceptionHandler instance;
    private final Logger logger = Logger.getLogger(this.getClass().getName());

    public static ExceptionHandler getExceptionHandler() {
        if (instance == null) {
            instance = new ExceptionHandler();
        }
        return instance;
    }

    public void handle(Exception e, Level level, String msg, Class<?> clazz) {
        logger.log(level, String.format("Class %s has encountered a problem: %s orginal error", clazz.getName(), msg), e);
    }

    public void handle(Exception e, String msg, Class<?> clazz) {
        this.handle(e, Level.SEVERE, msg, clazz);
    }
}
