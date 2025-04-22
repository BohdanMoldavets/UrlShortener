package com.moldavets.url_shortener_api.service.Impl;

import com.moldavets.url_shortener_api.exception.LinkExpiredException;
import com.moldavets.url_shortener_api.mapper.UrlMapper;
import com.moldavets.url_shortener_api.model.dto.url.UrlRequestDto;
import com.moldavets.url_shortener_api.model.dto.url.UrlResponseShortUrlDto;
import com.moldavets.url_shortener_api.model.entity.Impl.url.LinkStatus;
import com.moldavets.url_shortener_api.model.entity.Impl.url.Url;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.time.Instant;


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
            urlService.incrementUrlClicksByShortUrl(shortUrl);
            return URI.create(cachedLongUrl);
        }

        Url storedUrl = urlService.getByShortUrl(shortUrl);

        if(storedUrl.getExpiresDate().isAfter(Instant.now())) {
            String storedLongUrl = storedUrl.getLongUrl();
            cacheService.save(storedLongUrl, shortUrl);
            return URI.create(storedLongUrl);
        }

        if(storedUrl.getLinkStatus() == LinkStatus.ACTIVE) {
            urlService.updateUrlStatusById(LinkStatus.EXPIRED, storedUrl.getId());
        }
        throw new LinkExpiredException("The short link has expired");
    }

    public UrlResponseShortUrlDto createShortUrl(UrlRequestDto urlRequestDto) {
        String longUrl = urlRequestDto.getLongUrl();
        String shortUrl = this.createUniqueShortUrl();

        UrlResponseShortUrlDto responseShortUrlDto = UrlMapper.to(urlService.save(longUrl, shortUrl));
        cacheService.save(longUrl, shortUrl);
        return responseShortUrlDto;
    }

    public void deleteUrl(String shortUrl) {
        Url storedUrl = urlService.getByShortUrl(shortUrl);
        if(storedUrl != null && storedUrl.getLinkStatus() == LinkStatus.ACTIVE) {
            cacheService.deleteByShortUrl(shortUrl);
            urlService.updateUrlStatusById(LinkStatus.DELETED, storedUrl.getId());
        } else {
            throw new LinkExpiredException("The short link has expired");
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
