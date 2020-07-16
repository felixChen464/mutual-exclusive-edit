package com.ringcentral.assessment.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @Author cxh
 * @Date 2020/7/12
 */

@Configuration
public class JedisConfig {

    @Value("${spring.redis.host:127.0.0.1}")
    private String host;

    @Value("${spring.redis.port:6379}")
    private Integer port;

    @Value("${spring.redis.password:}")
    private String password;

    @Value("${spring.redis.database:0}")
    private Integer database;

    @Value("${spring.redis.jedis.pool.max-active:8}")
    private Integer maxActive;

    @Value("${spring.redis.jedis.pool.max-idle:8}")
    private Integer maxIdle;

    @Value("${spring.redis.jedis.pool.max-wait:-1}")
    private Long maxWait;

    @Value("${spring.redis.jedis.pool.min-idle:0}")
    private Integer minIdle;

    @Bean
    public JedisPoolConfig jedisPoolConfig() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(maxIdle);
        jedisPoolConfig.setMaxWaitMillis(maxWait);
        jedisPoolConfig.setMaxTotal(maxActive);
        jedisPoolConfig.setMinIdle(minIdle);
        return jedisPoolConfig;
    }


    @Bean
    public JedisPool getJedisPool(JedisPoolConfig jedisPoolConfig){
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxIdle(jedisPoolConfig.getMaxIdle());
        config.setMaxTotal(jedisPoolConfig().getMaxTotal());
        config.setMaxWaitMillis(jedisPoolConfig.getMaxWaitMillis());
        return new JedisPool(config,host,port,100);
    }
}
