package com.websocket;

public class CustomEvent {
    private String message;
    
    public CustomEvent(String message) {
        this.message = message;
    }
    
    public String getMessage() {
        return message;
    }
}