package com.gradlelab;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
public class SubTest {
    @Test public void testSub() {
        assertNotNull(new Sub().getString());
    }
}
