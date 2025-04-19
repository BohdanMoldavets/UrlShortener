package com.moldavets.url_shortener_api.service.Impl;

import com.moldavets.url_shortener_api.mapper.UrlMapper;
import com.moldavets.url_shortener_api.model.dto.UrlRequestDto;
import com.moldavets.url_shortener_api.model.dto.UrlResponseShortUrlDto;
import com.moldavets.url_shortener_api.service.UrlService;

import java.net.URI;


@org.springframework.stereotype.Service
public class UrlApplicationService {

    private final UrlServiceImpl urlService;
    private final ShortUrlGenerator shortUrlGenerator;
    //todo add cache in redis

    public UrlApplicationService(UrlServiceImpl urlService) {
        this.urlService = urlService;
        this.shortUrlGenerator = new ShortUrlGenerator();
    }

    public URI getLongUrl(String shortUrl) {
        return URI.create(urlService.getByShortUrl(shortUrl).getLongUrl());
    }

    public UrlResponseShortUrlDto createShortUrl(UrlRequestDto urlRequestDto) {
        String longUrl = urlRequestDto.getLongUrl();
        String shortUrl = shortUrlGenerator.generate();
        return UrlMapper.to(urlService.save(longUrl, shortUrl));
    }

}
