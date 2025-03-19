package com.example.collaborationsystem.controller;

import com.example.collaborationsystem.model.Message;
import com.example.collaborationsystem.model.WebRtcSignal;
import com.example.collaborationsystem.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    @Autowired
    private ChatService chatService;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/sendMessage")
    @SendTo("/topic/messages")
    public Message sendMessage(@RequestBody Message message) {
        chatService.saveMessage(message);
        return message;
    }

    @GetMapping("/history")
    public List<Message> getChatHistory(@RequestParam String userId, @RequestParam String groupId) {
        return chatService.getChatHistory(userId, groupId);
    }

    @PostMapping("/webrtc/offer")
    public void receiveOffer(@RequestBody WebRtcSignal offer) {
        chatService.handleOffer(offer);
    }

    @PostMapping("/webrtc/answer")
    public void receiveAnswer(@RequestBody WebRtcSignal answer) {
        chatService.handleAnswer(answer);
    }

    @PostMapping("/webrtc/candidate")
    public void receiveCandidate(@RequestBody WebRtcSignal candidate) {
        chatService.handleCandidate(candidate);
    }
}