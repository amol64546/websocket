package com.websocket;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class WebSocketController {

  @Autowired
  private SimpleWebSocketHandler webSocketHandler;

  @PostMapping("/broadcast")
  public ResponseEntity<String> broadcastMessage(@RequestBody Map<String, String> request) {
    String message = request.get("message");
    if (message != null && !message.trim().isEmpty()) {

      webSocketHandler.broadcastMessage("Server broadcast: " + message, null);
      return ResponseEntity.ok("Message broadcasted successfully");
    }
    return ResponseEntity.badRequest().body("Message cannot be empty");
  }
}
