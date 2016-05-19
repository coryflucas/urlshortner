package com.corylucas.urlshortner;

import static org.junit.Assert.*;

/**
 * Created by corylucas on 5/18/16.
 */
public class Base62ConverterTest {

    @org.junit.Test
    public void testFromBase10() {
        assertEquals("a", Base62Converter.fromBase10(0));
        assertEquals("b", Base62Converter.fromBase10(1));
        assertEquals("c", Base62Converter.fromBase10(2));
        assertEquals("A", Base62Converter.fromBase10(26));
        assertEquals("B", Base62Converter.fromBase10(27));
        assertEquals("C", Base62Converter.fromBase10(28));
        assertEquals("0", Base62Converter.fromBase10(52));
        assertEquals("1", Base62Converter.fromBase10(53));
        assertEquals("2", Base62Converter.fromBase10(54));
        assertEquals("ba", Base62Converter.fromBase10(62));
        assertEquals("baa", Base62Converter.fromBase10(62*62));
    }
}