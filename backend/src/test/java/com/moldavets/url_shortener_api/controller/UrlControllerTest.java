package com.moldavets.url_shortener_api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.moldavets.url_shortener_api.exception.LinkExpiredException;
import com.moldavets.url_shortener_api.model.dto.url.UrlRequestDto;
import com.moldavets.url_shortener_api.model.dto.url.UrlResponseInfoDto;
import com.moldavets.url_shortener_api.model.dto.url.UrlResponseShortUrlDto;
import com.moldavets.url_shortener_api.model.entity.Impl.url.LinkStatus;
import com.moldavets.url_shortener_api.service.Impl.UrlApplicationService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.net.URI;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@WebMvcTest(controllers = UrlController.class)
class UrlControllerTest {

    @MockitoBean
    private UrlApplicationService urlApplicationService;

    @MockitoBean
    private JpaMetamodelMappingContext jpaMetamodelMappingContext;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    private final String API_URL = "/api/v1/urls";
    private String shortUrl = "test";
    private String longUrl = "http://example.com";

    @Test
    void redirectLongUrl_shouldRedirectUserToLongUrl_whenShortUrlStoredInDb () throws Exception {
        when(urlApplicationService.getLongUrl(shortUrl)).thenReturn(URI.create(longUrl));
        mockMvc.perform(MockMvcRequestBuilders.get(API_URL + "/" + shortUrl))
                .andExpect(MockMvcResultMatchers.status().isMovedPermanently())
                .andExpect(MockMvcResultMatchers.header().string("Location", longUrl));

        verify(urlApplicationService, Mockito.times(1)).getLongUrl(anyString());
    }

    @Test
    void redirectLongUrl_shouldReturnNotFoundInfoInResponse_whenShortUrlDoesNotExist () throws Exception {
        when(urlApplicationService.getLongUrl(shortUrl))
                .thenThrow(new EntityNotFoundException(String.format("Entity with url - [%s] does not exist", shortUrl)));

        mockMvc.perform(MockMvcRequestBuilders.get(API_URL + "/" + shortUrl))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Link not found"));

        verify(urlApplicationService, Mockito.times(1)).getLongUrl(anyString());
    }

    @Test
    void redirectLongUrl_shouldReturnGoneInfoInResponse_whenShortUrlIsExpired () throws Exception {
        when(urlApplicationService.getLongUrl(shortUrl))
                .thenThrow(new LinkExpiredException("The short link has expired"));

        mockMvc.perform(MockMvcRequestBuilders.get(API_URL + "/" + shortUrl))
                .andExpect(MockMvcResultMatchers.status().isGone())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("The short link has expired"));

        verify(urlApplicationService, Mockito.times(1)).getLongUrl(anyString());
    }

    @Test
    void retrieveInfoOfShortUrl_shouldReturnInfoOfShortUrl_whenShortUrlStoredInDb () throws Exception {
        UrlResponseInfoDto responseInfoDto = new UrlResponseInfoDto(
                longUrl,
                shortUrl,
                Instant.now().plus(10, ChronoUnit.MINUTES),
                LinkStatus.ACTIVE,
                1L
        );

        when(urlApplicationService.getInfoByShortUrl(shortUrl))
                .thenReturn(responseInfoDto);

        mockMvc.perform(MockMvcRequestBuilders.get( API_URL + "/info/" + shortUrl))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.long_url").value(responseInfoDto.getLongUrl()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.short_url").value(responseInfoDto.getShortUrl()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.expires_date").value(responseInfoDto.getExpiresDate().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.link_status").value(responseInfoDto.getLinkStatus().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.total_clicks").value(responseInfoDto.getTotalClicks()));

        verify(urlApplicationService, Mockito.times(1)).getInfoByShortUrl(anyString());
    }

    @Test
    void retrieveInfoOfShortUrl_shouldReturnNotFoundInfoInResponse_whenShortUrlDoesNotExist() throws Exception {
        when(urlApplicationService.getInfoByShortUrl(shortUrl))
                .thenThrow(new EntityNotFoundException(String.format("Entity with url - [%s] does not exist", shortUrl)));

        mockMvc.perform(MockMvcRequestBuilders.get(API_URL + "/info/" + shortUrl))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Link not found"));

        verify(urlApplicationService, Mockito.times(1)).getInfoByShortUrl(anyString());
    }

    @Test
    void createShortUrl_shouldReturnShortUrl_whenInputContainsValidLongUrl () throws Exception {
        UrlRequestDto urlRequestDto = new UrlRequestDto(longUrl);
        UrlResponseShortUrlDto urlResponseShortUrlDto = new UrlResponseShortUrlDto(shortUrl);

        when(urlApplicationService.createShortUrl(urlRequestDto))
                .thenReturn(urlResponseShortUrlDto);

        mockMvc.perform(MockMvcRequestBuilders.post(API_URL)
                        .content(mapper.writeValueAsString(urlRequestDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.short_url").value(urlResponseShortUrlDto.getShortUrl()));

        verify(urlApplicationService, Mockito.times(1)).createShortUrl(urlRequestDto);
    }

    @Test
    void createShortUrl_shouldReturnBadRequestInfoInResponse_whenInputContainsNotValidLongUrl () throws Exception {
        UrlRequestDto urlRequestDto = new UrlRequestDto("test");

        mockMvc.perform(MockMvcRequestBuilders.post(API_URL)
                        .content(mapper.writeValueAsString(urlRequestDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Invalid link"));
    }
}