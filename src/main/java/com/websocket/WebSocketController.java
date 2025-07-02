package com.websocket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
  public ResponseEntity<String> broadcastMessage(@RequestBody Map<String, Object> messageJson) {
    if (messageJson != null && !messageJson.isEmpty()) {
      try {
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writeValueAsString(messageJson);
        System.out.println("Broadcasting message: " + jsonString);
        webSocketHandler.broadcastMessage(jsonString, null);
        return ResponseEntity.ok("Message broadcasted successfully");
      } catch (JsonProcessingException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to process message");
      }
    }
    return ResponseEntity.badRequest().body("Message cannot be empty");
  }
}
