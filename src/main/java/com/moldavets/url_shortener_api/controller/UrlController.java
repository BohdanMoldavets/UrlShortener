package com.moldavets.url_shortener_api.controller;

import com.moldavets.url_shortener_api.model.dto.UrlRequestDto;
import com.moldavets.url_shortener_api.model.dto.UrlResponseLongUrlDto;
import com.moldavets.url_shortener_api.model.dto.UrlResponseShortUrlDto;
import com.moldavets.url_shortener_api.service.Impl.UrlApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/urls")
@RequiredArgsConstructor
public class UrlController {

    private final UrlApplicationService urlApplicationService;

    @GetMapping("/{shortUrl}")
    public ResponseEntity<UrlResponseLongUrlDto> getLongUrl(@PathVariable("shortUrl") String shortUrl) {
        return new ResponseEntity<>(urlApplicationService.getLongUrl(shortUrl), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<UrlResponseShortUrlDto> createShortUrl(@RequestBody UrlRequestDto urlRequestDto) {
        return new ResponseEntity<>(urlApplicationService.createShortUrl(urlRequestDto),HttpStatus.CREATED);
    }
}
