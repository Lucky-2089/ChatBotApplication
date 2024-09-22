package com.example.chatbotapp.controller;

import com.example.chatbotapp.service.ChatbotService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/chatbot")
public class ChatbotController {

    private final ChatbotService chatbotService;

    public ChatbotController(ChatbotService chatbotService) {
        this.chatbotService = chatbotService;
    }

    @GetMapping("/page")
    public String chatbotPage() {
        return "chatbot"; // Returns the chatbot.html file
    }

    @PostMapping("/query")
    public ResponseEntity<String> handleQuery(@RequestBody String query) {
        String response = chatbotService.generateResponse(query);
        return ResponseEntity.ok(response); // Return response as JSON
    }
}