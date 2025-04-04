package com.example.ChatBot;

import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
@RequestMapping("app/chatbot")
public class ChatBotController {

	  @Autowired 
	  private OllamaChatModel chatClient;
	  
	    public ChatBotController(OllamaChatModel chatClient) {
	        this.chatClient = chatClient;
	    }
	    
	    
	    @PostMapping("/response")
	    public ResponseEntity<String> getResponse(@RequestBody MessageRequest request) {
	    	System.out.println(request);
	        if (request.getMessage() == null || request.getMessage().trim().isEmpty()) {
	            return ResponseEntity.badRequest().body("Message cannot be empty.");
	        }

	        try {
	            // Use Llama 3.2 3B model
//	                       String response = chatClient.prompt()
//                .model("llama3.2:3b")  // Change "mistral" to the model you are using
//                .message(request.getMessage())
//                .call()
//                .content();
//	        	  String response = chatClient.call(request.getMessage()).getResult().getOutput().getContent();
	        	String response = chatClient.call(request.getMessage());
//	            String response = chatClient.prompt(request).call().content();
	            return ResponseEntity.ok(response);
	        } catch (Exception e) {
	            return ResponseEntity.internalServerError().body("Error processing request: " + e.getMessage());
	        }
	    }

	    
	  // DTO class for handling JSON request body
	  public static class MessageRequest {
	        private String message;

	        public String getMessage() {
	            return message;
	        }

	        public void setMessage(String message) {
	            this.message = message;
	        }
	    }
}
