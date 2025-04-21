package com.moldavets.url_shortener_api.service.Impl;

import com.moldavets.url_shortener_api.mapper.UrlMapper;
import com.moldavets.url_shortener_api.model.dto.UrlRequestDto;
import com.moldavets.url_shortener_api.model.dto.UrlResponseShortUrlDto;
import org.springframework.stereotype.Service;

import java.net.URI;


@Service
public class UrlApplicationService {

    private final UrlServiceImpl urlService;
    private final ShortUrlGenerator shortUrlGenerator;
    private final CacheServiceImpl cacheService;

    public UrlApplicationService(UrlServiceImpl urlService, CacheServiceImpl cacheService) {
        this.urlService = urlService;
        this.cacheService = cacheService;
        this.shortUrlGenerator = new ShortUrlGenerator();
    }

    public URI getLongUrl(String shortUrl) {
        String cachedLongUrl = cacheService.getByShortUrl(shortUrl);
        if(cachedLongUrl != null) {
            return URI.create(cachedLongUrl);
        }
        URI redirectUri = URI.create(urlService.getByShortUrl(shortUrl).getLongUrl());
        cacheService.save(redirectUri.toASCIIString(), shortUrl);
        return redirectUri;
    }

    public UrlResponseShortUrlDto createShortUrl(UrlRequestDto urlRequestDto) {
        String longUrl = urlRequestDto.getLongUrl();
        String shortUrl = shortUrlGenerator.generate();

        UrlResponseShortUrlDto responseShortUrlDto = UrlMapper.to(urlService.save(longUrl, shortUrl));
        cacheService.save(longUrl, shortUrl);
        return responseShortUrlDto;
    }

}
