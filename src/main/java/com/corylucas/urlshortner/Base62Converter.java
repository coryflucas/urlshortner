package com.corylucas.urlshortner;

public class Base62Converter {
    public static final char[] ALPHABET = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z','A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z','0','1','2','3','4','5','6','7','8','9'};

    public static String fromBase10(long num) {
        if (num == 0) {
            return "a";
        }
        StringBuilder sb = new StringBuilder("");
        while (num > 0) {
            int rem = (int)(num % 62);
            sb.append(ALPHABET[rem]);
            num /= 62;
        }
        return sb.reverse().toString();
    }
}
