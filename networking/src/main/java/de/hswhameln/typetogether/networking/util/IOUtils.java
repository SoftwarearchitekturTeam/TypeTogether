package de.hswhameln.typetogether.networking.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public final class IOUtils {
    public static String getStringArgument(String argumentName, BufferedReader in, PrintWriter out) throws IOException {
        return getUntypedArgument(argumentName, String.class, in, out);
    }

    public static int getIntArgument(String argumentName, BufferedReader in, PrintWriter out) throws IOException {
        String untypedInput = getUntypedArgument(argumentName, Integer.class, in, out);
        return Integer.parseInt(untypedInput);
    }

    /**
     * Ask the client to provide an argument of the given type without parsing the result.
     *
     * @param argumentName Name of the argument
     * @param type         Type of the argument - only used to display the correct message.
     * @param in
     * @param out
     * @return A string representation of the given argument
     * @throws IOException On communication errors
     */
    private static String getUntypedArgument(String argumentName, Class<?> type, BufferedReader in, PrintWriter out) throws IOException {
        out.println("Provide a " + argumentName + " (" + type + ")");
        return in.readLine();
    }

}
