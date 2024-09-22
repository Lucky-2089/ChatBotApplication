package com.example.chatbotapp.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;


@Service
public class ChatbotService {
	private static final Logger logger = Logger.getLogger(ChatbotService.class.getName());
    private static Map<String, String> knowledgeBase = new HashMap<>();

    static {
        // Initialize some sample responses
    	 knowledgeBase.put("hello", "Hello! How can I assist you today?");
         knowledgeBase.put("hi", "Hi there! How can I help you?");
         knowledgeBase.put("how are you?", "I'm just a bot, but thanks for asking!");
         knowledgeBase.put("what is your name?", "I am a customer service bot.");
         knowledgeBase.put("what do you do?", "I assist you with your queries.");
         knowledgeBase.put("help", "Sure! What do you need help with?");
       
        // Add more as needed
    }

  public String generateResponse(String query) {
        // Log the incoming query for debugging
        System.out.println("Received query: " + query);
        
        // Generate a response based on the query
        String response = knowledgeBase.getOrDefault(query.toLowerCase(), "Sorry, I don't understand that.");
        
        // Log the generated response for debugging
        System.out.println("Generated response: " + response);
        
        return response;
    }

}