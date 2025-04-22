package com.moldavets.url_shortener_api.service.Impl;

import com.moldavets.url_shortener_api.exception.LinkExpiredException;
import com.moldavets.url_shortener_api.model.dto.url.UrlRequestDto;
import com.moldavets.url_shortener_api.model.dto.url.UrlResponseInfoDto;
import com.moldavets.url_shortener_api.model.dto.url.UrlResponseShortUrlDto;
import com.moldavets.url_shortener_api.model.entity.Impl.url.LinkStatus;
import com.moldavets.url_shortener_api.model.entity.Impl.url.Url;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.test.context.TestPropertySource;

import java.net.URI;
import java.time.Instant;
import java.time.temporal.ChronoUnit;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UrlApplicationServiceTest {

    private UrlServiceImpl urlService;
    private CacheServiceImpl cacheService;
    private UrlApplicationService urlApplicationService;

    private static String shortUrl;
    private static String longUrl;


    @BeforeAll
    static void init() {
        shortUrl = "shortUrl";
        longUrl = "http://example.com";
    }

    @BeforeEach
    void setUp() {
        urlService = mock(UrlServiceImpl.class);
        cacheService = mock(CacheServiceImpl.class);
        urlApplicationService = new UrlApplicationService(urlService, cacheService);
        urlApplicationService.applicationHostname = "hostname";
    }

    @Test
    void getLongUrl_shouldReturnLongUrlFromCache_whenUrlExistInCache() {
        when(cacheService.getByShortUrl(shortUrl)).thenReturn(longUrl);

        URI result = urlApplicationService.getLongUrl(shortUrl);

        assertEquals(URI.create(longUrl), result);
        verify(cacheService, Mockito.times(1)).getByShortUrl(shortUrl);
        verify(urlService).incrementUrlClicksByShortUrl(shortUrl);
        verifyNoMoreInteractions(urlService);
    }

    @Test
    void getLongUrl_shouldReturnLongUrlFromDatabase_whenUrlNotInCacheAndNotExpired() {
        Url url = new Url(longUrl, shortUrl, Instant.now().plusSeconds(2628000));

        when(cacheService.getByShortUrl(shortUrl)).thenReturn(null);
        when(cacheService.save(longUrl,shortUrl)).thenReturn(shortUrl);
        when(urlService.getByShortUrl(shortUrl)).thenReturn(url);

        URI result = urlApplicationService.getLongUrl(shortUrl);

        assertEquals(URI.create(longUrl), result);
        verify(cacheService, Mockito.times(1)).getByShortUrl(shortUrl);
        verify(cacheService, Mockito.times(1)).save(longUrl, shortUrl);
        verify(urlService, Mockito.times(1)).getByShortUrl(shortUrl);
        verify(urlService, Mockito.times(1)).incrementUrlClicksByShortUrl(shortUrl);
    }

    @Test
    void getLongUrl_shouldThrowException_whenLinkExpired() {
        LinkStatus status = LinkStatus.EXPIRED;
        Url url = new Url(longUrl, shortUrl, Instant.now().minus(1, ChronoUnit.MINUTES));

        when(cacheService.getByShortUrl(shortUrl)).thenReturn(null);
        when(urlService.getByShortUrl(shortUrl)).thenReturn(url);

        doNothing().when(urlService).updateUrlStatusById(status, url.getId());

        assertThrows(LinkExpiredException.class,
                () -> urlApplicationService.getLongUrl(shortUrl));
        verify(cacheService, Mockito.times(1)).getByShortUrl(shortUrl);
        verify(urlService, Mockito.times(1)).getByShortUrl(shortUrl);
        verify(urlService, Mockito.times(1)).updateUrlStatusById(status, url.getId());
    }

    @Test
    void getShortUrl_shouldThrowException_whenLinkDoesNotExist() {
        when(cacheService.getByShortUrl(shortUrl)).thenReturn(null);
        when(urlService.getByShortUrl(shortUrl))
                .thenThrow(new EntityNotFoundException(String.format("Entity with url - [%s] does not exist", shortUrl)));

        assertThrows(EntityNotFoundException.class,
                () -> urlApplicationService.getLongUrl(shortUrl));
        verify(cacheService, Mockito.times(1)).getByShortUrl(shortUrl);
        verify(urlService, Mockito.times(1)).getByShortUrl(shortUrl);
    }

    @Test
    void getInfoByShortUrl_shouldReturnInfoAboutLink_whenLinkExist() {
        Url url = new Url(longUrl, shortUrl, Instant.now().plus(10, ChronoUnit.MINUTES));

        when(urlService.getByShortUrl(shortUrl)).thenReturn(url);

        UrlResponseInfoDto actual = urlApplicationService.getInfoByShortUrl(shortUrl);

        assertEquals(url.getLongUrl(), actual.getLongUrl());
        assertEquals(url.getShortUrl(), actual.getShortUrl());
        assertEquals(url.getExpiresDate(), actual.getExpiresDate());
        assertEquals(url.getLinkStatus(), actual.getLinkStatus());
        assertEquals(url.getTotalClicks(), actual.getTotalClicks());
        verify(urlService, Mockito.times(1)).getByShortUrl(shortUrl);
    }

    @Test
    void getInfoByShortUrl_shouldThrowException_whenLinkDoesNotExist() {
        when(urlService.getByShortUrl(shortUrl))
                .thenThrow(new EntityNotFoundException(String.format("Entity with url - [%s] does not exist", shortUrl)));

        assertThrows(EntityNotFoundException.class,
                () -> urlApplicationService.getInfoByShortUrl(shortUrl));

        verify(urlService, Mockito.times(1)).getByShortUrl(shortUrl);
    }

    @Test
    void createShortUrl_shouldReturnShortUrlAlsoSaveToDatabaseAndCache_whenInputContainsValidLongUrl() {
        when(urlService.save(anyString(), anyString()))
                .thenReturn(new Url(longUrl, shortUrl, Instant.now().plus(10, ChronoUnit.MINUTES)));
        when(urlService.getByShortUrl(anyString()))
                .thenThrow(new EntityNotFoundException(String.format("Entity with url - [%s] does not exist", shortUrl)));
        when(cacheService.save(anyString(), anyString())).thenReturn(shortUrl);

        String expected = String.format("http://%s/%s", urlApplicationService.applicationHostname, shortUrl);
        UrlResponseShortUrlDto actual = urlApplicationService.createShortUrl(new UrlRequestDto(longUrl));

        assertEquals(expected, actual.getShortUrl());
        verify(urlService, Mockito.times(1)).save(anyString(), anyString());
        verify(cacheService, Mockito.times(1)).save(anyString(), anyString());
    }
}