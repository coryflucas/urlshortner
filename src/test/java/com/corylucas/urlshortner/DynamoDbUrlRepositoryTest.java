package com.corylucas.urlshortner;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.model.*;
import com.corylucas.urlshortner.models.ConflictException;
import org.junit.Test;

import static info.solidsoft.mockito.java8.AssertionMatcher.*;
import static info.solidsoft.mockito.java8.LambdaMatcher.*;
import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

/**
 * Created by corylucas on 5/18/16.
 */
public class DynamoDbUrlRepositoryTest {

    @Test
    public void testStore() throws Exception {
        AmazonDynamoDB mockDb = mock(AmazonDynamoDB.class);

        DynamoDbUrlRepository repo = new DynamoDbUrlRepository(mockDb);
        repo.store("test", "http://example.org");

        verify(mockDb).putItem(assertArg(r -> {
            assertEquals("test", r.getItem().get("UrlKey").getS());
            assertEquals("http://example.org", r.getItem().get("Url").getS());
        }));
    }

    @Test(expected = ConflictException.class)
    public void testStoreThrowsConflictExceptionOnDuplicate() throws Exception {
        AmazonDynamoDB mockDb = mock(AmazonDynamoDB.class);
        when(mockDb.putItem(any()))
                .thenThrow(ConditionalCheckFailedException.class);

        DynamoDbUrlRepository repo = new DynamoDbUrlRepository(mockDb);
        repo.store("test", "http://example.org");
    }

    @Test
    public void testFind() {
        AmazonDynamoDB mockDb = mock(AmazonDynamoDB.class);
        given(mockDb.getItem(argLambda(r -> r.getKey().get("UrlKey").getS().equals("test"))))
                .willReturn(new GetItemResult().addItemEntry("Url", new AttributeValue("http://example.org")));

        DynamoDbUrlRepository repo = new DynamoDbUrlRepository(mockDb);
        String result = repo.find("test");

        assertEquals("http://example.org", result);
    }

    @Test
    public void testFindReturnsNullIfNoMatch(){
        AmazonDynamoDB mockDb = mock(AmazonDynamoDB.class);
        given(mockDb.getItem(argLambda(r -> r.getKey().get("UrlKey").getS().equals("test"))))
                .willReturn(new GetItemResult());

        DynamoDbUrlRepository repo = new DynamoDbUrlRepository(mockDb);
        String result = repo.find("test");

        assertNull(result);
    }
}