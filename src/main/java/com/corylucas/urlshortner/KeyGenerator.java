package com.corylucas.urlshortner;

import java.util.zip.CRC32;

public class KeyGenerator {
    public static String GenerateKey(String url) {
        CRC32 crc = new CRC32();
        crc.update(url.getBytes());
        long checksum = crc.getValue();
        return Base62Converter.fromBase10(checksum);
    }
}
