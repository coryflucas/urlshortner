package com.corylucas.urlshortner.models;

public class ConflictException extends Exception {
    public ConflictException(String message)
    {
        super(message);
    }
}
