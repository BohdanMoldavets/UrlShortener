package com.moldavets.url_shortener_api.service.Impl;

import com.moldavets.url_shortener_api.mapper.UrlMapper;
import com.moldavets.url_shortener_api.model.dto.UrlRequestDto;
import com.moldavets.url_shortener_api.model.dto.UrlResponseDto;
import com.moldavets.url_shortener_api.service.UrlService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
public class UrlApplicationService {

    private final UrlService urlService;
    private final ShortUrlGenerator shortUrlGenerator;

    public UrlApplicationService(UrlService urlService) {
        this.urlService = urlService;
        this.shortUrlGenerator = new ShortUrlGenerator();
    }

    public UrlResponseDto createShortUrl(UrlRequestDto urlRequestDto) {
        String longUrl = urlRequestDto.getLongUrl();
        String shortUrl = shortUrlGenerator.generate();

        return UrlMapper.to(urlService.save(longUrl, shortUrl));
    }

}
