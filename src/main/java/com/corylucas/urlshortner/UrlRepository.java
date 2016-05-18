package com.corylucas.urlshortner;

public interface UrlRepository {
    void store(String key, String url);
    String find(String key);
}
