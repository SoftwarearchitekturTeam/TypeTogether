package de.hswhameln.typetogether.networking.util;

import de.hswhameln.typetogether.networking.types.ParseException;

import java.util.function.Function;
import java.util.regex.Pattern;

public class StringRepresentationSchema<T> {
    private final String prefix;
    private final String suffix;
    private final String delimiter;
    private final int elementCount;

    private final Class<T> targetClass;

    public StringRepresentationSchema(Class<T> targetClass, String prefix, String suffix, String delimiter, int elementCount) {
        this.targetClass = targetClass;
        this.prefix = prefix;
        this.suffix = suffix;
        this.delimiter = delimiter;
        this.elementCount = elementCount;
    }

    public String getStringRepresentation(String... rawData) {
        return this.prefix + String.join(this.delimiter, rawData) + this.suffix;
    }

    public T parse(String stringRepresentation, Function<String[], T> factoryMethod) {
        if (!stringRepresentation.startsWith(this.prefix) || !stringRepresentation.endsWith(this.suffix)) {
            throw new ParseException(this.targetClass, stringRepresentation, "Must be wrapped in " + this.prefix + " and " + this.suffix + ".");
        }
        String innerContent = stringRepresentation.substring(1, stringRepresentation.length() - 1);
        String[] elements = innerContent.split(Pattern.quote(this.delimiter));
        if (elements.length != this.elementCount) {
            throw new ParseException(
                    this.targetClass, stringRepresentation, "Must contain exactly " + this.elementCount + " elements, seperated by " + this.delimiter);
        }
        return factoryMethod.apply(elements);
    }
}
