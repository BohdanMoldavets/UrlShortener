package com.moldavets.url_shortener_api.controller;

import com.moldavets.url_shortener_api.model.dto.url.UrlRequestDto;
import com.moldavets.url_shortener_api.model.dto.url.UrlResponseShortUrlDto;
import com.moldavets.url_shortener_api.service.Impl.UrlApplicationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/urls")
@RequiredArgsConstructor
public class UrlController {

    private final UrlApplicationService urlApplicationService;

    @GetMapping("/{shortUrl}")
    public ResponseEntity<Void> redirectLongUrl(@PathVariable("shortUrl") String shortUrl) {
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(urlApplicationService.getLongUrl(shortUrl));
        return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
    }

    @PostMapping
    public ResponseEntity<UrlResponseShortUrlDto> createShortUrl(@Valid @RequestBody UrlRequestDto urlRequestDto) {
        return new ResponseEntity<>(urlApplicationService.createShortUrl(urlRequestDto),HttpStatus.CREATED);
    }
}
