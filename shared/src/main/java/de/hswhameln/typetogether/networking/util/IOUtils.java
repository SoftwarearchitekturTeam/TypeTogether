package de.hswhameln.typetogether.networking.util;

import de.hswhameln.typetogether.networking.proxy.ResponseCodes;
import de.hswhameln.typetogether.networking.types.DocumentCharacter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Logger;

public final class IOUtils {
    private static final Logger logger = LoggerFactory.getLogger(IOUtils.class);

    public static String getStringArgument(String argumentName, BufferedReader in, PrintWriter out) throws IOException {
        return getUntypedArgument(argumentName, String.class, in, out);
    }

    public static int getIntArgument(String argumentName, BufferedReader in, PrintWriter out) throws IOException {
        String untypedInput = getUntypedArgument(argumentName, Integer.class, in, out);
        return Integer.parseInt(untypedInput);
    }

    public static DocumentCharacter getDocumentCharacterArgument(String argumentName, BufferedReader in, PrintWriter out) throws IOException {
        String untypedInput = getUntypedArgument(argumentName, DocumentCharacter.class, in, out);
        logger.finer("Input received for argument " + argumentName + ": " + untypedInput);
        return DocumentCharacter.parse(untypedInput);
    }

    public static void success(PrintWriter out) {
        out.println(ResponseCodes.SUCCESS);
    }

    public static void error(String responseCode, String message, PrintWriter out) {
        out.println(responseCode);
        out.println(message);
    }

    /**
     * Ask the client to provide an argument of the given type without parsing the result.
     *
     * @param argumentName Name of the argument
     * @param type         Type of the argument - only used to display the correct message.
     * @param in           BufferedReader for client communication
     * @param out          PrintWriter for client communication
     * @return A string representation of the given argument
     * @throws IOException On communication errors
     */
    private static String getUntypedArgument(String argumentName, Class<?> type, BufferedReader in, PrintWriter out) throws IOException {
        out.println("Provide a " + argumentName + " (" + type + ")");
        return in.readLine();
    }
}
