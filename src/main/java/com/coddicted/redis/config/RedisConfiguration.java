package com.coddicted.redis.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
public class RedisConfiguration {
    // @Value("${spring.redis.host}")
    // private String REDIS_HOSTNAME;
    // @Value("${spring.redis.port}")
    // private int REDIS_PORT;
    // @Bean
    // protected JedisConnectionFactory jedisConnectionFactory() {
    //     RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration(REDIS_HOSTNAME, REDIS_PORT);
    //     JedisClientConfiguration jedisClientConfiguration = JedisClientConfiguration.builder().usePooling().build();
    //     JedisConnectionFactory factory = new JedisConnectionFactory(configuration,jedisClientConfiguration);
    //     factory.afterPropertiesSet();
    //     return factory;
    // }

    @Value("${redis.sentinel.master.name}")
    private String MASTER_NAME;
    
    @Value("${redis.sentinel.host.and.ports}")
    private String HOST_AND_PORTS;

    @Bean(name = "jedisConnectionFactory")
    public JedisConnectionFactory jedisConnectionFactory() {
        RedisSentinelConfiguration redisSentinelConfiguration = new RedisSentinelConfiguration();
        redisSentinelConfiguration.master(MASTER_NAME);
        String redisSentinelHostAndPorts = HOST_AND_PORTS;
        HostAndPort hostAndPort;
        if (redisSentinelHostAndPorts.contains(";")) {
            for (String node : redisSentinelHostAndPorts.split(";")) {
                if (null != node & node.contains(":")) {
                    String [] nodeArr = node.split(":");
                    System.out.println(Arrays.toString(nodeArr));
                    hostAndPort = new HostAndPort(nodeArr[0], Integer.parseInt(nodeArr[1]));
                    HostAndPort.setLocalhost("127.0.0.1");
                    redisSentinelConfiguration.sentinel(hostAndPort.getHost(), hostAndPort.getPort());
                }
            }
        } else {
            if (redisSentinelHostAndPorts.contains(":")) {
                String [] nodeArr = redisSentinelHostAndPorts.split(":");
                hostAndPort = new HostAndPort(nodeArr[0], Integer.parseInt(nodeArr[1]));
                redisSentinelConfiguration.sentinel(hostAndPort.getHost(), hostAndPort.getPort());
            }
        }
        return new JedisConnectionFactory(redisSentinelConfiguration, poolConfig());
    }

    @Bean
    public JedisPoolConfig poolConfig() {
        final JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setTestOnBorrow(true);
        jedisPoolConfig.setMaxTotal(100);
        jedisPoolConfig.setMaxIdle(100);
        jedisPoolConfig.setMinIdle(10);
        jedisPoolConfig.setTestOnReturn(true);
        jedisPoolConfig.setTestWhileIdle(true);
        return jedisPoolConfig;
    }
    
    @Bean
    public RedisTemplate<String,Object> redisTemplate() {
        final RedisTemplate<String,Object> redisTemplate = new RedisTemplate<String,Object>();
        redisTemplate.setConnectionFactory(jedisConnectionFactory());
        return redisTemplate;
    }
}