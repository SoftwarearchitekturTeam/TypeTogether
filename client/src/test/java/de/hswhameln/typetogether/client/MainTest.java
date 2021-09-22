package de.hswhameln.typetogether.client;

import org.junit.jupiter.api.Test;

import de.hswhameln.typetogether.client.Main;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MainTest {

    @Test
    void testSetup() {
        Main main = new Main();
        assertEquals("ping", main.ping());
    }
}