package com.corylucas.urlshortner;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.model.*;
import com.corylucas.urlshortner.models.ConflictException;

import java.util.Map;

public class DynamoDbUrlRepository implements UrlRepository {
    private static String TABLE_NAME = "ShortUrl";
    private AmazonDynamoDB dynamoDB;

    public DynamoDbUrlRepository(AmazonDynamoDB dynamoDB) {
        this.dynamoDB  = dynamoDB;
    }

    @Override
    public void store(String key, String url) throws ConflictException {
        try {
            PutItemRequest request = new PutItemRequest()
                    .withTableName(TABLE_NAME)
                    .addItemEntry("UrlKey", new AttributeValue(key))
                    .addItemEntry("Url", new AttributeValue(url))
                    .withConditionExpression("attribute_not_exists(" + TABLE_NAME + ".UrlKey)");
            dynamoDB.putItem(request);
        }
        catch (ConditionalCheckFailedException e) {
            throw new ConflictException("Entry with key already exists");
        }
    }

    @Override
    public String find(String key) {
        GetItemRequest request = new GetItemRequest()
                .withTableName(TABLE_NAME)
                .addKeyEntry("UrlKey", new AttributeValue(key));
        GetItemResult result = dynamoDB.getItem(request);
        Map<String, AttributeValue> item = result.getItem();

        if(item == null) {
            return null;
        }
        return item.get("Url").getS();
    }
}
