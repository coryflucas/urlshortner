package com.corylucas.urlshortner.models;

/**
 * Created by corylucas on 5/17/16.
 */
public class CreateShortUrlResponse {
    private String shortUrl;

    public CreateShortUrlResponse(String shortUrl) {
        setShortUrl(shortUrl);
    }

    public String getShortUrl() {
        return shortUrl;
    }

    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }
}
