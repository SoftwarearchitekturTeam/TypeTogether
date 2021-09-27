package de.hswhameln.typetogether.client;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Simple command line argument parser that can handle letters and digits.
 * Usage example: java -jar myjar.jar --port=7777 --url=http://my-url.com/
 */
public class ArgumentParser {

    private static final String PREFIX = "--";

    public Map<String, String> parse(String[] args) {

        return Arrays.stream(args)
                .map(this::parseArgument)
                .filter(Objects::nonNull)
                .collect(Collectors.toMap(Argument::getKey, Argument::getValue));

    }

    private Argument parseArgument(String s) {
        if (!s.startsWith(PREFIX)) {
            return null;
        }
        String[] keyAndValue = s.substring(PREFIX.length()).split("=");
        return new Argument(keyAndValue[0], keyAndValue[1]);
    }

    private static class Argument {
        private final String key;
        private final String value;

        private Argument(String key, String value) {
            this.key = key;
            this.value = value;
        }

        public String getKey() {
            return key;
        }

        public String getValue() {
            return value;
        }
    }
}
