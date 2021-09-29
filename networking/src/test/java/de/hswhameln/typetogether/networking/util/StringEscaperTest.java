package de.hswhameln.typetogether.networking.util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;

import static org.junit.jupiter.api.Assertions.*;

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
    void testEscapeTab() {
        assertEquals("a\\tb", StringEscaper.escape("a\tb"));
    }

    @Test
    void testUnescapeTab() {
        assertEquals("a\tb", StringEscaper.unescape("a\\tb"));
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