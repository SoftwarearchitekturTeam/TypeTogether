package de.hswhameln.typetogether.networking;

import de.hswhameln.typetogether.networking.api.exceptions.FunctionalException;
import de.hswhameln.typetogether.networking.proxy.ResponseCodes;
import de.hswhameln.typetogether.networking.util.ExceptionHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;

public class FluentExceptionHandler {

    private final String responseCode;
    private final String className;
    private final String message;

    private FluentExceptionHandler(String responseCode, String className, String message) {
        this.responseCode = responseCode;
        this.className = className;
        this.message = message;
    }

    public <T extends FunctionalException> FluentExceptionHandler andHandleError(Class<T> functionalExceptionClass) throws T {
        if (!ResponseCodes.FUNCTIONAL_ERROR.equals(this.responseCode)) {
            return this;
        }
        if (!functionalExceptionClass.getName().equals(this.className)) {
            return this;
        }

        T e;
        try {
            Constructor<T> constructor = functionalExceptionClass.getConstructor(String.class);
            e = constructor.newInstance(this.message);
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException ex) {
            ExceptionHandler.getExceptionHandler().handle(ex, Level.WARNING, "Could not create exception " + this.className + ".", FluentExceptionHandler.class);
            return this;
        }
        throw e;
    }

    public static FluentExceptionHandler expectSuccess(BufferedReader in) throws IOException {
        String responseCode = in.readLine();
        if (ResponseCodes.SUCCESS.equals(responseCode)) {
            return new FluentExceptionHandler(responseCode, null, null);
        }
        if (ResponseCodes.FUNCTIONAL_ERROR.equals(responseCode)) {
            String className = in.readLine();
            String message = in.readLine();
            return new FluentExceptionHandler(responseCode, className, message);
        }
        if (ResponseCodes.UNEXPECTED_ERROR.equals(responseCode)) {
            String message = in.readLine();
            throw new RuntimeException(
                    "Unexpected server side error: The server answered with response code " + responseCode + ". Message: " + message);
        }
        // unexpected response code
        throw new RuntimeException(
                "Unexpected server side error with an unexpected response code: The server answered with response code "
                        + responseCode + ".");
    }
}
