package com.example.collaborationsystem.service;

import com.example.collaborationsystem.model.User;
import com.example.collaborationsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public ResponseEntity<String> registerUser(User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            return ResponseEntity.badRequest().body("用户名已存在");
        }
        if (!isValidEmail(user.getEmail())) {
            return ResponseEntity.badRequest().body("邮箱格式错误");
        }
        userRepository.save(user);
        return ResponseEntity.ok("注册成功");
    }

    public ResponseEntity<String> loginUser(User user) {
        User existingUser = userRepository.findByUsername(user.getUsername());
        if (existingUser == null) {
            return ResponseEntity.badRequest().body("用户名不存在");
        }
        if (!existingUser.getPassword().equals(user.getPassword())) {
            return ResponseEntity.badRequest().body("密码错误");
        }
        return ResponseEntity.ok("登录成功");
    }

    public ResponseEntity<String> updateUser(Long id, User user) {
        User existingUser = userRepository.findById(id).orElse(null);
        if (existingUser == null) {
            return ResponseEntity.badRequest().body("用户不存在");
        }
        if (userRepository.existsByUsernameAndIdNot(user.getUsername(), id)) {
            return ResponseEntity.badRequest().body("用户名已存在");
        }
        if (!isValidEmail(user.getEmail())) {
            return ResponseEntity.badRequest().body("邮箱格式错误");
        }
        existingUser.setUsername(user.getUsername());
        existingUser.setPassword(user.getPassword());
        existingUser.setEmail(user.getEmail());
        existingUser.setAvatar(user.getAvatar());
        userRepository.save(existingUser);
        return ResponseEntity.ok("修改成功");
    }

    public ResponseEntity<String> deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            return ResponseEntity.badRequest().body("用户不存在");
        }
        userRepository.deleteById(id);
        return ResponseEntity.ok("删除成功");
    }

    public ResponseEntity<List<User>> getContacts() {
        List<User> contacts = userRepository.findAll();
        return ResponseEntity.ok(contacts);
    }

    private boolean isValidEmail(String email) {
        return email.contains("@");
    }
}