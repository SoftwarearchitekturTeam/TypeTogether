package de.hswhameln.typetogether.networking.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StringEscaperTest {

    @Test
    void testEscapeHappyDay() {
        assertEquals("abc", StringEscaper.escape("abc"));
    }

    @Test
    void testUnescapeHappyDay() {
        assertEquals("abc", StringEscaper.unescape("abc"));
    }

    @Test
    void testEscapeNewLine() {
        assertEquals("a\\nb", StringEscaper.escape("a\nb"));
    }

    @Test
    void testUnescapeNewLine() {
        assertEquals("a\nb", StringEscaper.unescape("a\\nb"));
    }

    @Test
    void testEscapeBackslash() {
        assertEquals("a\\\\b", StringEscaper.escape("a\\b"));
    }

    @Test
    void testUnescapeBackslash() {
        assertEquals("a\\b", StringEscaper.unescape("a\\\\b"));
    }
}