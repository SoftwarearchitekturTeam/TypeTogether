package com.mycompany.myproject.library;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MyLibraryTest {

    @Test
    void testSetup() {
        MyLibrary main = new MyLibrary();
        assertEquals("ping", main.ping());
    }
}