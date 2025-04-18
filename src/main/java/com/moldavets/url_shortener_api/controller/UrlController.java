package com.moldavets.url_shortener_api.controller;

import com.moldavets.url_shortener_api.model.dto.UrlRequestDto;
import com.moldavets.url_shortener_api.model.dto.UrlResponseDto;
import com.moldavets.url_shortener_api.service.Impl.UrlApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/urls")
@RequiredArgsConstructor
public class UrlController {

    private final UrlApplicationService urlApplicationService;

    @PostMapping
    public ResponseEntity<UrlResponseDto> createUrl(@RequestBody UrlRequestDto urlRequestDto) {
        return new ResponseEntity<>(urlApplicationService.createShortUrl(urlRequestDto),HttpStatus.CREATED);
    }
}
