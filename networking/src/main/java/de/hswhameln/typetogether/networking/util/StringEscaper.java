package de.hswhameln.typetogether.networking.util;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class StringEscaper {

    private static final Map<String, Character> escapedChars = getStringCharacterLinkedHashMap();

    private static LinkedHashMap<String, Character> getStringCharacterLinkedHashMap() {
        LinkedHashMap<String, Character> map = new LinkedHashMap<>();
        map.put("\\", '\\' );
        map.put("\n", 'n' );
        return map;
    }
    private static final Map<Character, String> escapedCharsReversed = escapedChars.entrySet().stream().collect(Collectors.toMap(Map.Entry::getValue, Map.Entry::getKey));

    public static String escape(String input) {
        String result = input;
        for (var charAndReplacement: escapedChars.entrySet()) {
            result = result.replace(charAndReplacement.getKey(), "\\" + charAndReplacement.getValue());
        }
        return result;
    }

    public static String unescape(String input) {
        boolean backslash = false;
        StringBuilder result = new StringBuilder();
        for (char c: input.toCharArray()) {
            if (c == '\\' && backslash) {
                result.append(c);
                backslash = false;
            } else if (c == '\\') {
                backslash = true;
                // don't append to result
            } else if (backslash) {
                result.append(escapedCharsReversed.get(c));
                backslash = false;
            } else {
                result.append(c);
            }
        }
        return result.toString();

    }
}
