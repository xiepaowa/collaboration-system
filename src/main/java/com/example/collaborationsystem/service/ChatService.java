package com.example.collaborationsystem.service;

import com.example.collaborationsystem.model.Message;
import com.example.collaborationsystem.model.WebRtcSignal;
import com.example.collaborationsystem.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatService {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    public void saveMessage(Message message) {
        messageRepository.save(message);
    }

    public List<Message> getChatHistory(String userId, String groupId) {
        if (groupId != null) {
            return messageRepository.findByGroupId(groupId);
        } else {
            return messageRepository.findByUserId(userId);
        }
    }

    public void handleOffer(WebRtcSignal offer) {
        messagingTemplate.convertAndSend("/topic/webrtc/offer", offer);
    }

    public void handleAnswer(WebRtcSignal answer) {
        messagingTemplate.convertAndSend("/topic/webrtc/answer", answer);
    }

    public void handleCandidate(WebRtcSignal candidate) {
        messagingTemplate.convertAndSend("/topic/webrtc/candidate", candidate);
    }
}