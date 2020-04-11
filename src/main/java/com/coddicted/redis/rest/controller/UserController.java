package com.coddicted.redis.rest.controller;

import java.util.List;

import com.coddicted.redis.model.User;
import com.coddicted.redis.rest.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    @CachePut(value = "users", key = "#user.id")
    public User createUser(@RequestBody User user) {
        log.info("createUser");
        userService.createUser(user);
        return user;
    }

    @GetMapping
    public List<User> getUsers() {
        return userService.getUsers();
    }

    @GetMapping("/{id}")
    @Cacheable(value = "users", key = "#id")
    public User getUser(@PathVariable String id){
        log.info("getUser: "+ id);
        return userService.getUser(id);
    }

    @CacheEvict(value = "users", allEntries=true)
    @DeleteMapping("/{id}")
    public void deleteUserById(@PathVariable String id) {
        log.info("deleting person with id {}", id);
        userService.deleteUserById(id);
    }
}