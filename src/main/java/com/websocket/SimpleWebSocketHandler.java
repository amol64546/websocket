package com.websocket;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
public class SimpleWebSocketHandler extends TextWebSocketHandler {
    
    private final static Set<WebSocketSession> sessions = Collections.synchronizedSet(new HashSet<>());
    
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.add(session);
        System.out.println("Connection established: " + session.getId());
        
        // Send welcome message
        session.sendMessage(new TextMessage("Welcome! Connection established."));
    }
    
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) {
        String payload = message.getPayload();
        System.out.println("Received message: " + payload);
    }
    
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessions.remove(session);
        System.out.println("Connection closed: " + session.getId());
    }
    
    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        System.out.println("Transport error for session " + session.getId() + ": " + exception.getMessage());
        sessions.remove(session);
    }
    
    // Method to broadcast message to all connected sessions
    public void broadcastMessage(String message, WebSocketSession excludeSession) {
        TextMessage textMessage = new TextMessage(message);
        sessions.forEach(session -> {
            if (session.isOpen()) {
                try {
                    session.sendMessage(textMessage);
                } catch (Exception e) {
                    System.err.println("Error sending message to session " + session.getId() + ": " + e.getMessage());
                }
            }
        });
    }
}