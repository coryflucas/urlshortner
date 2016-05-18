package com.corylucas.urlshortner;

import java.util.HashMap;

public class HashMapUrlRepository implements UrlRepository {
    private HashMap<String, String> urls;

    public HashMapUrlRepository() {
        urls = new HashMap<>();
    }

    @Override
    public void store(String key, String url) {
        urls.put(key, url);
    }

    @Override
    public String find(String key) {
        return urls.get(key);
    }
}
