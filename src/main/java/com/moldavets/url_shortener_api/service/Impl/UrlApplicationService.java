package com.moldavets.url_shortener_api.service.Impl;

import com.moldavets.url_shortener_api.mapper.UrlMapper;
import com.moldavets.url_shortener_api.model.dto.UrlRequestDto;
import com.moldavets.url_shortener_api.model.dto.UrlResponseLongUrlDto;
import com.moldavets.url_shortener_api.model.dto.UrlResponseShortUrlDto;
import com.moldavets.url_shortener_api.service.UrlService;
import org.springframework.stereotype.Service;


@Service
public class UrlApplicationService {

    private final UrlService urlService;
    private final ShortUrlGenerator shortUrlGenerator;

    public UrlApplicationService(UrlService urlService) {
        this.urlService = urlService;
        this.shortUrlGenerator = new ShortUrlGenerator();
    }

    public UrlResponseLongUrlDto getLongUrl(String shortUrl) {
        return new UrlResponseLongUrlDto(urlService.getByShortUrl(shortUrl).getLongUrl());
    }

    public UrlResponseShortUrlDto createShortUrl(UrlRequestDto urlRequestDto) {
        String longUrl = urlRequestDto.getLongUrl();
        String shortUrl = shortUrlGenerator.generate();
        return UrlMapper.to(urlService.save(longUrl, shortUrl));
    }

}
