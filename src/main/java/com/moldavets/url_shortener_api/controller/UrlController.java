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
@RequiredArgsConstructor
public class UrlController {

    private final UrlApplicationService urlApplicationService;

    @GetMapping("/{shortUrl}")
    public ResponseEntity<Void> redirectLongUrl(@PathVariable("shortUrl") String shortUrl) {
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(urlApplicationService.getLongUrl(shortUrl));
        return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
    }

    @PostMapping("/api/v1/urls")
    public ResponseEntity<UrlResponseShortUrlDto> createShortUrl(@Valid @RequestBody UrlRequestDto urlRequestDto) {
        return new ResponseEntity<>(urlApplicationService.createShortUrl(urlRequestDto),HttpStatus.CREATED);
    }

    @DeleteMapping("/api/v1/urls/{shortUrl}")
    public ResponseEntity<Void> deleteShortUrl(@PathVariable("shortUrl") String shortUrl) {
        urlApplicationService.deleteUrl(shortUrl);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
