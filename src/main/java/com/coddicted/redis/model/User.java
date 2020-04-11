package com.coddicted.redis.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class User implements Serializable {
    private String id;
    private String name;
    private int age;
}