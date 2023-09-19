package com.gradle.lab;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class Main2Test {

    @Test
    void appHasAGreeting1() throws InterruptedException {
        Main classUnderTest = new Main();
        assertNotNull(classUnderTest.getMessage(), "main should have a greeting");

        Thread.sleep(300);
    }

    @Test
    void appHasAGreeting2() throws InterruptedException {
        Main classUnderTest = new Main();
        assertNotNull(classUnderTest.getMessage(), "main should have a greeting");

        Thread.sleep(300);
    }

    @Test
    void appHasAGreeting3() throws InterruptedException {
        Main classUnderTest = new Main();
        assertNotNull(classUnderTest.getMessage(), "main should have a greeting");

        Thread.sleep(300);
    }
}
