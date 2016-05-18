package com.corylucas.urlshortner;

import com.corylucas.urlshortner.models.ConflictException;

public interface UrlRepository {
    void store(String key, String url) throws ConflictException;
    String find(String key);
}
