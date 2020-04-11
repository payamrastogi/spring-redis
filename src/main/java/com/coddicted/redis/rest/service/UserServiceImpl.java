package com.coddicted.redis.rest.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.coddicted.redis.entity.UserEntity;
import com.coddicted.redis.model.User;
import com.coddicted.redis.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;

    @Override
    public User getUser(String id){
        Optional<UserEntity> optional = userRepository.findById(id);
        User user = null;
        if(optional.isPresent()){
            UserEntity userEntity = optional.get();
            user = new User();
            user.setId(userEntity.getId());
            user.setName(userEntity.getName());
            user.setAge(userEntity.getAge());
        }
        return user;
    }

    @Override
    public void createUser(User user){
        if(user!=null){
            UserEntity userEntity = new UserEntity();
            userEntity.setId(user.getId());
            userEntity.setName(user.getName());
            userEntity.setAge(user.getAge());
            userRepository.save(userEntity);
        }
    }

    @Override
    public List<User> getUsers(){
        List<UserEntity> userEntityList = userRepository.findAll();
        List<User> userList = new ArrayList<>();
        if(userEntityList!=null && !userEntityList.isEmpty()){
            for(UserEntity userEntity : userEntityList){
                User user = new User();
                user.setId(userEntity.getId());
                user.setName(userEntity.getName());
                user.setAge(userEntity.getAge());
                userList.add(user);
            }
        }
        return userList;
    }

    @Override
    public void deleteUserById(String id){
        userRepository.deleteById(id);
    }

}