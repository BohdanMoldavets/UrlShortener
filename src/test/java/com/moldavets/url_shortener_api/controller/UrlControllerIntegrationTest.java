package com.moldavets.url_shortener_api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.moldavets.url_shortener_api.model.dto.url.UrlRequestDto;
import com.redis.testcontainers.RedisContainer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@Testcontainers
@SpringBootTest(properties = "spring.profiles.active=test")
@AutoConfigureMockMvc(printOnlyOnFailure = false)
class UrlControllerIntegrationTest {

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15");

    @Container
    static RedisContainer redis = new RedisContainer(DockerImageName.parse("redis:6.2.6"))
            .withExposedPorts(6379);

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    private final String API_URL = "/api/v1/urls/";

    @DynamicPropertySource
    static void setUpProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
        registry.add("spring.data.redis.host", redis::getHost);
        registry.add("spring.data.redis.port", () -> redis.getMappedPort(6379).toString());
    }

    @ParameterizedTest
    @CsvSource({
            "http://www.example.com, PnGyot",
            "http://www.example.pl, fgwOrk",
            "http://www.example.de, koZjFA",
            "http://www.example.ua, oRZGxo",
    })
    void redirectLongUrl_shouldReturnLongUrl_whenInputContainsStoredShortUrlInDb(String longUrl, String shortUrl) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(API_URL + shortUrl))
                .andExpect(MockMvcResultMatchers.status().isMovedPermanently())
                .andExpect(MockMvcResultMatchers.header().string("Location", longUrl));
    }

    @ParameterizedTest
    @CsvSource({
            "PnGyo2",
            "fgwOrX",
            "koZjFk",
    })
    void redirectLongUrl_shouldReturnNotFoundErrorInfoInResponse_whenInputContainsNotExistShortUrlInDb(String shortUrl) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(API_URL + shortUrl))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Link not found"));
    }

    @ParameterizedTest
    @CsvSource({
            "URMLhx",
    })
    void redirectLongUrl_shouldReturnGoneErrorInfoInResponse_whenInputContainsExpiredShortUrlInDb(String shortUrl) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(API_URL + shortUrl))
                .andExpect(MockMvcResultMatchers.status().isGone())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("The short link has expired"));
    }

    @ParameterizedTest
    @CsvSource({
            "http://www.example.com, PnGyot, ACTIVE, 3",
            "http://www.example.pl, fgwOrk, ACTIVE, 2",
            "http://www.example.de, koZjFA, ACTIVE, 2",
            "http://www.example.ua, oRZGxo, ACTIVE, 1",
            "http://www.example.io, URMLhx, EXPIRED, 0",
    })
    void retrieveInfoOfShortUrl_shouldReturnOkAndInfoAboutUrlInResponse_whenInputContainsShortUrlStoredInDb(String longUrl,
                                                                                                            String shortUrl,
                                                                                                            String linkStatus,
                                                                                                            Long totalClicks) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(API_URL+ "info/" + shortUrl))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.long_url").value(longUrl))
                .andExpect(MockMvcResultMatchers.jsonPath("$.short_url").value(shortUrl))
                .andExpect(MockMvcResultMatchers.jsonPath("$.link_status").value(linkStatus))
                .andExpect(MockMvcResultMatchers.jsonPath("$.total_clicks").value(totalClicks));
    }

    @ParameterizedTest
    @CsvSource({
            "PnGyo2",
            "fgwOrX",
            "koZjFk",
    })
    void retrieveInfoOfShortUrl_shouldReturnNotFoundErrorInfoInResponse_whenInputContainsNotExistShortUrl(String shortUrl) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/urls/info/"+ shortUrl))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Link not found"));
    }

    @ParameterizedTest
    @CsvSource({
            "http://www.source.com",
            "http://www.source.pl",
            "https://www.source.de",
            "https://www.source.ua",
            "https://www.source.io"
    })
    void createShortUrl_shouldReturnShortUrl_whenInputContainsValidLongUrl(String longUrl) throws Exception {
        UrlRequestDto requestDto = new UrlRequestDto(longUrl);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/urls")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andDo(resultMvc ->
                        Assertions.assertEquals(34, resultMvc.getResponse().getContentAsString().length()));
    }



}