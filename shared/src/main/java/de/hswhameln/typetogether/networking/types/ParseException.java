package de.hswhameln.typetogether.networking.types;

public class ParseException extends IllegalArgumentException {
    public ParseException(Class<?> targetClazz, String input, String message) {
        super("Could not parse String '" + input + "' to " + targetClazz.getSimpleName() + ": " + message);
    }

    public ParseException(Class<Identifier> targetClazz, String input, NumberFormatException e) {
        super("Could not parse String '" + input + "' to " + targetClazz.getSimpleName() + ".", e);
    }
}
