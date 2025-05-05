package com.moldavets.url_shortener_api.controller;

import com.moldavets.url_shortener_api.model.dto.url.UrlRequestDto;
import com.moldavets.url_shortener_api.model.dto.url.UrlResponseInfoDto;
import com.moldavets.url_shortener_api.model.dto.url.UrlResponseLongUrlDto;
import com.moldavets.url_shortener_api.model.dto.url.UrlResponseShortUrlDto;
import com.moldavets.url_shortener_api.model.entity.Impl.url.LinkStatus;
import com.moldavets.url_shortener_api.service.Impl.UrlApplicationService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class UrlControllerUnitTest {

    @InjectMocks
    private UrlController controller;

    @Mock
    private UrlApplicationService urlApplicationService;

    @Test
    void redirectLongUrl_shouldReturnResponseEntityWithLongUrl_whenUrlStoredInDatabase() {
        URI storedLongUrl = URI.create("http://example.com/");

        when(urlApplicationService.getLongUrl("test"))
                .thenReturn(storedLongUrl);

        ResponseEntity<UrlResponseLongUrlDto> actual = controller.redirectLongUrl("test");

        assertEquals(HttpStatus.OK, actual.getStatusCode());
        verify(urlApplicationService, Mockito.times(1)).getLongUrl(anyString());
    }

    @Test
    void redirectShortUrl_shouldThrowException_whenLongUrlDoesNotExist() {
        when(urlApplicationService.getLongUrl("test"))
                .thenThrow(new EntityNotFoundException("Link not found"));

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> controller.redirectLongUrl("test"));

        assertEquals("Link not found", exception.getMessage());
        verify(urlApplicationService, Mockito.times(1)).getLongUrl(anyString());
    }

    @Test
    void retrieveInfoOfShortUrl_shouldReturnResponseEntityWithUrlInfo_whenUrlStoredInDatabase() {
        UrlResponseInfoDto urlInfo = new UrlResponseInfoDto(
                "http://example.com/",
                "test",
                Instant.now().plus(10, ChronoUnit.MINUTES),
                LinkStatus.ACTIVE,
                1L
        );

        when(urlApplicationService.getInfoByShortUrl("test"))
                .thenReturn(urlInfo);

        ResponseEntity<UrlResponseInfoDto> actual = controller.retrieveInfoOfShortUrl("test");

        assertEquals(urlInfo.getShortUrl(), actual.getBody().getShortUrl());
        assertEquals(urlInfo.getLongUrl(), actual.getBody().getLongUrl());
        assertEquals(urlInfo.getExpiresDate(), actual.getBody().getExpiresDate());
        assertEquals(urlInfo.getLinkStatus(), actual.getBody().getLinkStatus());
        assertEquals(urlInfo.getTotalClicks(), actual.getBody().getTotalClicks());
        assertEquals(HttpStatus.OK, actual.getStatusCode());
        verify(urlApplicationService, Mockito.times(1)).getInfoByShortUrl("test");
    }

    @Test
    void retrieveInfoOfShortUrl_shouldThrowException_whenUrlDoesNotExist() {
        when(urlApplicationService.getInfoByShortUrl("test"))
                .thenThrow(new EntityNotFoundException("Link not found"));

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> controller.retrieveInfoOfShortUrl("test"));

        assertEquals("Link not found", exception.getMessage());
        verify(urlApplicationService, Mockito.times(1)).getInfoByShortUrl("test");
    }

    @Test
    void createShortUrl_shouldReturnShortUrl_whenInputContainsValidLongUrl() {
        UrlRequestDto inputDto = new UrlRequestDto("http://example.com/");
        UrlResponseShortUrlDto createdShortUrl = new UrlResponseShortUrlDto("test");

        when(urlApplicationService.createShortUrl(inputDto))
                .thenReturn(createdShortUrl);

        ResponseEntity<UrlResponseShortUrlDto> actual = controller.createShortUrl(inputDto);

        assertEquals(createdShortUrl.getShortUrl(), actual.getBody().getShortUrl());
        assertEquals(HttpStatus.CREATED, actual.getStatusCode());
        verify(urlApplicationService, Mockito.times(1)).createShortUrl(inputDto);
    }
}