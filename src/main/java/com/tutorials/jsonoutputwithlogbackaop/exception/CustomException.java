package com.tutorials.jsonoutputwithlogbackaop.exception;

public class CustomException  extends RuntimeException{
    private String message;

    public CustomException(String message) {
        super(message);
    }
}
