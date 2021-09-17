package com.chat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.chat.model.ChatMessage;



@Controller
public class ChatSendController {
	@Autowired
	private SimpMessagingTemplate messagingTemplate;
	

	@MessageMapping("/chat.register")
	@SendTo("/topic/public")
	public ChatMessage register(@Payload ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor) {
		headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
		return chatMessage;
	}


	@MessageMapping("/chat.send")
	@SendTo("/topic/public")
	public ChatMessage sendMessage(@Payload ChatMessage chatMessage) {
		return chatMessage;
	}
	
	
    //@MessageMapping("/chat.send")
    //@SendToUser("/topic/public")
    public void send(@Payload ChatMessage chatMessage) {
        //return chatMessage;
    	messagingTemplate.convertAndSend("/topic/public-" + chatMessage.getSender(), chatMessage);
    }	
	
	@MessageExceptionHandler
	@SendTo("/topic/public")
    public String handleException(Throwable exception) {
        return exception.getMessage();
    }	

}
