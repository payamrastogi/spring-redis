package com.coddicted.redis.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name="users")
@Data
public class UserEntity {
    @Id
    private String id;
    private String name;
    private int age;
}