package com.moldavets.url_shortener_api.controller;

import com.moldavets.url_shortener_api.model.entity.Impl.url.Url;
import com.moldavets.url_shortener_api.repository.UrlRepository;
import com.redis.testcontainers.RedisContainer;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Disabled
@Testcontainers
@SpringBootTest(properties = "spring.profiles.active=test")
@AutoConfigureMockMvc(printOnlyOnFailure = false)
class UrlControllerIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    UrlRepository urlRepository;

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15");

    @Container
    static RedisContainer redis = new RedisContainer(DockerImageName.parse("redis:6.2.6"))
            .withExposedPorts(6379);

    @DynamicPropertySource
    static void setUpProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
        registry.add("spring.data.redis.host", redis::getHost);
        registry.add("spring.data.redis.port", () -> redis.getMappedPort(6379).toString());
    }

    @Test
    public void test () throws Exception {
        urlRepository.save(new Url("123","321", Instant.now().plus(10, ChronoUnit.MINUTES)));
        System.out.println(urlRepository.findAll());
    }

}