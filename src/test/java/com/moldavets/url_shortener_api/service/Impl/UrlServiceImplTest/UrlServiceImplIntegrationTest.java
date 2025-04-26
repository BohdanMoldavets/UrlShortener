package com.moldavets.url_shortener_api.service.Impl.UrlServiceImplTest;

import com.moldavets.url_shortener_api.model.entity.Impl.url.LinkStatus;
import com.moldavets.url_shortener_api.model.entity.Impl.url.Url;
import com.moldavets.url_shortener_api.service.Impl.UrlServiceImpl;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Testcontainers
@SpringBootTest(
        properties = "spring.profiles.active=test",
        webEnvironment = SpringBootTest.WebEnvironment.NONE)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UrlServiceImplIntegrationTest {

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15");

    @Autowired
    private UrlServiceImpl urlServiceImpl;

    @Autowired
    private EntityManager entityManager;

    private final Long id = 1L;
    private final String longUrl = "http://example.com";
    private final String shortUrl = "test";

    @DynamicPropertySource
    static void setUpProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Test
    @Order(1)
    void save_shouldSaveUrlToDatabase_whenInputContainsValidData(){
        urlServiceImpl.save(longUrl, shortUrl);

        TypedQuery<Url> query =
                entityManager.createQuery("SELECT u FROM Url u WHERE u.shortUrl = :shortUrl", Url.class);

        Url resultUrl = query.setParameter("shortUrl", shortUrl).getSingleResult();

        assertEquals(id, resultUrl.getId());
        assertEquals(longUrl, resultUrl.getLongUrl());
        assertEquals(shortUrl, resultUrl.getShortUrl());
    }

    @Test
    @Order(2)
    void getByShortUrl_shouldRetrieveUrlFromDatabase_whenInputContainsStoredShortUrl(){
        Url resultUrl = urlServiceImpl.getByShortUrl(shortUrl);

        assertEquals(id, resultUrl.getId());
        assertEquals(longUrl, resultUrl.getLongUrl());
        assertEquals(shortUrl, resultUrl.getShortUrl());
    }

    @Test
    @Order(3)
    void updateUrlStatusById_shouldUpdateUrlStatus_whenInputContainsStoredShortUrl(){
        urlServiceImpl.updateUrlStatusById(LinkStatus.EXPIRED, id);

        TypedQuery<Url> query =
                entityManager.createQuery("SELECT u FROM Url u WHERE u.shortUrl = :shortUrl", Url.class);

        Url resultUrl = query.setParameter("shortUrl", shortUrl).getSingleResult();

        assertEquals(id, resultUrl.getId());
        assertEquals(longUrl, resultUrl.getLongUrl());
        assertEquals(shortUrl, resultUrl.getShortUrl());
        assertEquals(LinkStatus.EXPIRED, resultUrl.getLinkStatus());
    }

    @Test
    @Order(4)
    void incrementUrlClicksByShortUrl_shouldIncrementTotalClicks_whenInputContainsStoredShortUrl(){
        Long totalClicks =
                entityManager.createQuery("SELECT u FROM Url u WHERE u.shortUrl = :shortUrl", Url.class)
                                .setParameter("shortUrl", shortUrl)
                                        .getSingleResult()
                                                .getTotalClicks();

        urlServiceImpl.incrementUrlClicksByShortUrl(shortUrl);
        urlServiceImpl.incrementUrlClicksByShortUrl(shortUrl);

        TypedQuery<Url> query =
                entityManager.createQuery("SELECT u FROM Url u WHERE u.shortUrl = :shortUrl", Url.class);

        Url resultUrl = query.setParameter("shortUrl", shortUrl).getSingleResult();

        assertEquals(longUrl, resultUrl.getLongUrl());
        assertEquals(shortUrl, resultUrl.getShortUrl());
        assertEquals(totalClicks + 2, resultUrl.getTotalClicks());
    }



}
