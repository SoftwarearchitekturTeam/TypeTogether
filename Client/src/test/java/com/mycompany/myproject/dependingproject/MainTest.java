package com.mycompany.myproject.dependingproject;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MainTest {

    @Test
    void testSetup() {
        Main main = new Main();
        assertEquals("ping", main.ping());
    }
}