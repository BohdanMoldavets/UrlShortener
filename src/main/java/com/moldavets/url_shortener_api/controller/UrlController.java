package com.moldavets.url_shortener_api.controller;

import com.moldavets.url_shortener_api.model.dto.url.UrlRequestDto;
import com.moldavets.url_shortener_api.model.dto.url.UrlResponseInfoDto;
import com.moldavets.url_shortener_api.model.dto.url.UrlResponseShortUrlDto;
import com.moldavets.url_shortener_api.service.Impl.UrlApplicationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;


@Slf4j
@RestController
@RequiredArgsConstructor
public class UrlController {

    private final UrlApplicationService urlApplicationService;

    @GetMapping("/{shortUrl}")
    public ResponseEntity<Void> redirectLongUrl(@PathVariable("shortUrl") String shortUrl) {
        URI longUrl = urlApplicationService.getLongUrl(shortUrl);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(longUrl);
        log.info("Redirecting to - [{}]. Short url is - [{}]", longUrl, shortUrl);
        return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
    }

    @GetMapping("api/v1/urls/info/{shortUrl}")
    public ResponseEntity<UrlResponseInfoDto> retrieveInfoOfShortUrl(@PathVariable("shortUrl") String shortUrl) {
        UrlResponseInfoDto infoByShortUrl = urlApplicationService.getInfoByShortUrl(shortUrl);
        log.info("Retrieving info about short url - [{}]", shortUrl);
        return new ResponseEntity<>(infoByShortUrl, HttpStatus.OK);
    }

    @PostMapping("/api/v1/urls")
    public ResponseEntity<UrlResponseShortUrlDto> createShortUrl(@Valid @RequestBody UrlRequestDto urlRequestDto) {
        UrlResponseShortUrlDto createdShortUrl = urlApplicationService.createShortUrl(urlRequestDto);
        log.info("Short url - [{}], created for long url - [{}]", createdShortUrl.getShortUrl(), urlRequestDto.getLongUrl());
        return new ResponseEntity<>(createdShortUrl,HttpStatus.CREATED);
    }

    //TODO DELETE BEFORE PRODUCTION
    @DeleteMapping("/api/v1/urls/{shortUrl}")
    public ResponseEntity<Void> deleteShortUrl(@PathVariable("shortUrl") String shortUrl) {
        urlApplicationService.deleteUrl(shortUrl);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
