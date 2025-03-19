package com.example.collaborationsystem.repository;

import com.example.collaborationsystem.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findByUserId(String userId);
    List<Message> findByGroupId(String groupId);
}