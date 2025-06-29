package com.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {
    
    @Autowired
    private SimpleWebSocketHandler webSocketHandler;
    
    // Send notification to all connected clients
    public void sendNotificationToAll(String message) {
        webSocketHandler.broadcastMessage(message, null);
    }
    
    // This could be triggered by various events in your application
    @EventListener
    public void handleCustomEvent(CustomEvent event) {
        sendNotificationToAll("New event: " + event.getMessage());
    }
}
