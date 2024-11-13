package com.vision_hackathon.cheollian.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;

import groovy.util.logging.Slf4j;

@Slf4j
@Configuration
public class RedisConfig {

	private static final Logger log = LoggerFactory.getLogger(RedisConfig.class);
	@Value("${spring.data.redis.host}")
	private String host;

	@Value("${spring.data.redis.port}")
	private int port;

	@Value("${spring.data.redis.password}")
	private String password;

	@Bean
	public RedisConnectionFactory redisConnectionFactory() {
		RedisStandaloneConfiguration config = new RedisStandaloneConfiguration(host, port);
		config.setPassword(password);
		return new LettuceConnectionFactory(config);
	}
}
