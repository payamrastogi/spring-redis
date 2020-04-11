package com.coddicted.redis.rest.service;

import java.util.List;

import com.coddicted.redis.model.User;

public interface UserService {
   User getUser(String id);
   void createUser(User user);
   List<User> getUsers();
   void deleteUserById(String id);
}