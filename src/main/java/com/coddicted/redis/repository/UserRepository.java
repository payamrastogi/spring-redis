package com.coddicted.redis.repository;

import java.util.Optional;
import com.coddicted.redis.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, String> {
    public Optional<UserEntity> findById(String id);
}