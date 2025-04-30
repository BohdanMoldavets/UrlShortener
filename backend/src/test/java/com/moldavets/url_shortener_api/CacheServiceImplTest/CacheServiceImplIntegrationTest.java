package com.moldavets.url_shortener_api.CacheServiceImplTest;

import com.moldavets.url_shortener_api.service.Impl.CacheServiceImpl;
import com.redis.testcontainers.RedisContainer;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.redis.DataRedisTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@Testcontainers
@DataRedisTest
@Import(CacheServiceImpl.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CacheServiceImplIntegrationTest {

    @MockitoBean
    private JpaMetamodelMappingContext jpaMetamodelMappingContext;

    @Container
    static RedisContainer redis = new RedisContainer(DockerImageName.parse("redis:6.2.6"))
            .withExposedPorts(6379);

    @Autowired
    RedisTemplate<String, String> redisTemplate;

    @Autowired
    CacheServiceImpl cacheServiceImpl;

    private final String longUrl = "http://example.com";
    private final String shortUrl = "test";

    @DynamicPropertySource
    static void setUpProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.redis.host", redis::getHost);
        registry.add("spring.data.redis.port", () -> redis.getMappedPort(6379).toString());
    }

    @Test
    @Order(1)
    void save_shouldSaveDataToCache_whenInputContainsValidData() {
        cacheServiceImpl.save(longUrl, shortUrl);

        String actual = redisTemplate.opsForValue().get(shortUrl);

        assertEquals(longUrl, actual);
    }

    @Test
    @Order(2)
    void getByShortUrl_shouldReturnDataFromCache_whenInputContainsStoredShortUrl() {
        String actual = cacheServiceImpl.getByShortUrl(shortUrl);

        assertEquals(longUrl, actual);
    }

    @Test
    @Order(4)
    void getByShortUrl_shouldReturnNull_whenInputContainsNotExistingShortUrl() {
        String actual = cacheServiceImpl.getByShortUrl(shortUrl);

        assertNull(actual);
    }

    @Test
    @Order(3)
    void deleteByShortUrl_shouldDeleteDataFromCache_whenInputContainsStoredShortUrl() {
        cacheServiceImpl.deleteByShortUrl(shortUrl);

        String actual = redisTemplate.opsForValue().get(shortUrl);

        assertNull(actual);
    }
}
