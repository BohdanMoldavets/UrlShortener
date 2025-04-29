package com.moldavets.url_shortener_api.exception.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.moldavets.url_shortener_api.controller.UrlController;
import com.moldavets.url_shortener_api.exception.LinkExpiredException;
import com.moldavets.url_shortener_api.model.dto.url.UrlRequestDto;
import com.moldavets.url_shortener_api.service.Impl.UrlApplicationService;
import io.lettuce.core.RedisConnectionException;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@WebMvcTest(controllers = UrlController.class)
@Import({CustomExceptionHandler.class})
class CustomExceptionHandlerTest {

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    UrlApplicationService urlApplicationService;

    @MockitoBean
    private JpaMetamodelMappingContext jpaMetamodelMappingContext;

    @Autowired
    ObjectMapper objectMapper;

    private final String API_URL = "/api/v1/urls";
    private final String shortUrl = "test";
    private final UrlRequestDto urlRequestDto = new UrlRequestDto("not valid link");

    @Test
    void handleMethodArgumentNotValidException_shouldHandleMethodArgumentNotValidException() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(API_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(urlRequestDto)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Invalid link"));

        verify(urlApplicationService, never()).createShortUrl(Mockito.any(UrlRequestDto.class));
    }

    @Test
    void handleEntityNotFoundException_shouldHandleEntityNotFoundException() throws Exception {
        when(urlApplicationService.getLongUrl(shortUrl))
                .thenThrow(new EntityNotFoundException(String.format("Entity with url - [%s] does not exist", shortUrl)));

        mockMvc.perform(MockMvcRequestBuilders.get(API_URL + "/" + shortUrl))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Link not found"));

        verify(urlApplicationService, Mockito.times(1)).getLongUrl(anyString());
    }

    @Test
    void handleLinkExpiredException_shouldHandleLinkExpiredException() throws Exception {
        when(urlApplicationService.getLongUrl(shortUrl))
                .thenThrow(new LinkExpiredException("The short link has expired"));

        mockMvc.perform(MockMvcRequestBuilders.get(API_URL + "/" + shortUrl))
                .andExpect(MockMvcResultMatchers.status().isGone())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("The short link has expired"));

        verify(urlApplicationService, Mockito.times(1)).getLongUrl(anyString());
    }

    @Test
    void handleAllExceptions_shouldHandleUnexpectedExceptions() throws Exception {
        when(urlApplicationService.getLongUrl(shortUrl))
                .thenThrow(new RedisConnectionException("Redis connection error"));

        mockMvc.perform(MockMvcRequestBuilders.get(API_URL + "/" + shortUrl))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("An unexpected error occurred. Please try again later"));

        verify(urlApplicationService, Mockito.times(1)).getLongUrl(anyString());
    }

    @Test
    void handleAllExceptions_() throws Exception {
        when(urlApplicationService.getLongUrl(shortUrl))
                .thenThrow(new NullPointerException("Input data cannot be null"));

        mockMvc.perform(MockMvcRequestBuilders.get(API_URL + "/" + shortUrl))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Input data cannot be null"));

        verify(urlApplicationService, Mockito.times(1)).getLongUrl(anyString());
    }

}