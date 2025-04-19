package com.moldavets.url_shortener_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootApplication
@EnableJpaAuditing
public class UrlShortenerApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(UrlShortenerApiApplication.class, args);
	}

}
