package com.example.collaborationsystem.model;

import lombok.Data;

@Data
public class WebRtcSignal {
    private String type; // "offer", "answer", "candidate"
    private String sdp; // Session Description Protocol
    private String candidate; // ICE Candidate
    private String senderId;
    private String receiverId;
    private String groupId;
}