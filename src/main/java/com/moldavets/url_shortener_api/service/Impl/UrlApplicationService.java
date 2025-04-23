package com.moldavets.url_shortener_api.service.Impl;

import com.moldavets.url_shortener_api.exception.LinkExpiredException;
import com.moldavets.url_shortener_api.mapper.UrlMapper;
import com.moldavets.url_shortener_api.model.dto.url.UrlRequestDto;
import com.moldavets.url_shortener_api.model.dto.url.UrlResponseInfoDto;
import com.moldavets.url_shortener_api.model.dto.url.UrlResponseShortUrlDto;
import com.moldavets.url_shortener_api.model.entity.Impl.url.LinkStatus;
import com.moldavets.url_shortener_api.model.entity.Impl.url.Url;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.time.Instant;


@Service
public class UrlApplicationService {

    private final UrlServiceImpl urlService;
    private final ShortUrlGenerator shortUrlGenerator;
    private final CacheServiceImpl cacheService;

    @Value("${application.hostname}")
    String applicationHostname;

    public UrlApplicationService(UrlServiceImpl urlService, CacheServiceImpl cacheService, ShortUrlGenerator shortUrlGenerator) {
        this.urlService = urlService;
        this.cacheService = cacheService;
        this.shortUrlGenerator = shortUrlGenerator;
    }

    public URI getLongUrl(String shortUrl) {
        String cachedLongUrl = cacheService.getByShortUrl(shortUrl);
        if(cachedLongUrl != null) {
            urlService.incrementUrlClicksByShortUrl(shortUrl);
            return URI.create(cachedLongUrl);
        }

        Url storedUrl = urlService.getByShortUrl(shortUrl);

        if(storedUrl.getExpiresDate().isAfter(Instant.now())) {
            String storedLongUrl = storedUrl.getLongUrl();
            cacheService.save(storedLongUrl, shortUrl);
            urlService.incrementUrlClicksByShortUrl(shortUrl);
            return URI.create(storedLongUrl);
        }

        if(storedUrl.getLinkStatus() == LinkStatus.ACTIVE) {
            urlService.updateUrlStatusById(LinkStatus.EXPIRED, storedUrl.getId());
        }
        throw new LinkExpiredException("The short link has expired");
    }

    public UrlResponseInfoDto getInfoByShortUrl(String shortUrl) {
        Url storedUrl = urlService.getByShortUrl(shortUrl);
        return new UrlResponseInfoDto(
                storedUrl.getLongUrl(),
                shortUrl,
                storedUrl.getExpiresDate(),
                storedUrl.getLinkStatus(),
                storedUrl.getTotalClicks()
        );
    }

    public UrlResponseShortUrlDto createShortUrl(UrlRequestDto urlRequestDto) {
        String longUrl = urlRequestDto.getLongUrl();
        String shortUrl = this.createUniqueShortUrl();

        Url storedUrl = urlService.save(longUrl, shortUrl);

        UrlResponseShortUrlDto responseShortUrlDto =
                new UrlResponseShortUrlDto(storedUrl.getShortUrl());

        responseShortUrlDto.setShortUrl(String.format("http://%s/%s", applicationHostname, responseShortUrlDto.getShortUrl()));

        cacheService.save(longUrl, shortUrl);
        return responseShortUrlDto;
    }

    public void deleteUrl(String shortUrl) {
        Url storedUrl = urlService.getByShortUrl(shortUrl);
        if(storedUrl.getLinkStatus() == LinkStatus.ACTIVE) {
            cacheService.deleteByShortUrl(shortUrl);
            urlService.updateUrlStatusById(LinkStatus.DELETED, storedUrl.getId());
        }
    }


    private String createUniqueShortUrl() {
        String shortUrl;

        while (true) {
            shortUrl = shortUrlGenerator.generate();
            try {
                urlService.getByShortUrl(shortUrl);
            } catch (EntityNotFoundException e) {
                break;
            }
        }
        return shortUrl;
    }
}
