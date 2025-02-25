package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/get")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/get/{id}")
    public User getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @GetMapping("/username/{username}")
    public List<User> getUsersByUsername(@PathVariable String username) {
        return userService.getUsersByUsername(username);
    }

    @PostMapping("/post")
    public User createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    @PostMapping("/postmultiple")
    public List<User> createMultipleUsers(@RequestBody List<User> users) {
        return userService.createMultipleUsers(users);
    }

    @PutMapping("/put/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User user) {
        return userService.updateUser(id, user);
    }

    @PatchMapping("/update-username/{id}")
    public int updateUsername(@PathVariable Long id, @RequestParam String username) {
        return userService.updateUsername(id, username);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }
}
