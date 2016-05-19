package com.corylucas.urlshortner;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.corylucas.urlshortner.models.ConflictException;
import com.corylucas.urlshortner.models.CreateShortUrlRequest;
import com.corylucas.urlshortner.models.CreateShortUrlResponse;
import com.google.gson.Gson;
import org.apache.commons.validator.routines.UrlValidator;

import static spark.Spark.*;

public class Main {
    public static void main(String[] args) {
        Gson gson = new Gson();

        AmazonDynamoDBClient dynamoDB = new AmazonDynamoDBClient()
                .withRegion(Regions.US_EAST_1);
        UrlRepository urlRepo = new DynamoDbUrlRepository(dynamoDB);

        UrlValidator urlValidator = new UrlValidator(new String[] {"http", "https"});

        port(5000);

        post("/api/shorturl", (req, res) -> {
            String contentType = req.contentType();
            if(contentType == null || !req.contentType().equals("application/json")) {
                res.status(415);
                return "Unsupported Media Type: use application/json";
            }
            CreateShortUrlRequest request = gson.fromJson(req.body(), CreateShortUrlRequest.class);
            String url = request.getUrl();
            if(!urlValidator.isValid(url)) {
                res.status(400);
                return "Invalid URL";
            }

            String key;
            while(true) {
                key = KeyGenerator.GenerateKey(url);
                String existingUrl = urlRepo.find(key);
                if(existingUrl == null || existingUrl.equals(url)) {
                    break;
                }
                url = url + "_";
            }

            try {
                urlRepo.store(key, request.getUrl());
            } catch (ConflictException e) {
                // this should only happen due to race of two people requesting to shorten the same or conflicting urls
                res.status(409);
                return "Conflict";
            }

            String shortUrl = String.format("%s://%s/%s", req.scheme(), req.host(), key);
            CreateShortUrlResponse response = new CreateShortUrlResponse(shortUrl);
            return gson.toJson(response);
        });

        get("/:key", (req, res) -> {
            String key = req.params(":key");
            String url = urlRepo.find(key);
            if(url == null) {
                res.status(404);
                return "<h1>Not Found</h1>";
            } else {
                res.redirect(url);
                return "";
            }
        });
    }
}
