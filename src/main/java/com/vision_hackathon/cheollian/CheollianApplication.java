package com.vision_hackathon.cheollian;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@SpringBootApplication
@EnableRedisRepositories(basePackages = "com.vision_hackathon.cheollian.auth.domain.persistence")
@EnableJpaRepositories(basePackages = "com.vision_hackathon.cheollian")
public class CheollianApplication {

	public static void main(String[] args) {
		SpringApplication.run(CheollianApplication.class, args);
	}

}
