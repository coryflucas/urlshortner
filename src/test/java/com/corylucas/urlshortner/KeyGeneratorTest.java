package com.corylucas.urlshortner;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by corylucas on 5/18/16.
 */
public class KeyGeneratorTest {

    @Test
    public void testGenerateKey() {
        assertEquals("kQeYl", KeyGenerator.GenerateKey("test_string"));
    }
}