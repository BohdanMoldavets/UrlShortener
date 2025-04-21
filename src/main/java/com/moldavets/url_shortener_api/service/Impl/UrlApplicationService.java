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

        Url storedUrl = urlService.getByShortUrl(shortUrl);

        if(storedUrl != null && storedUrl.getLinkStatus() == LinkStatus.ACTIVE) {
            urlService.updateUrlStatusById(LinkStatus.EXPIRED, storedUrl.getId());
        }

        throw new LinkExpiredException("The short link has expired");
    }

    public UrlResponseShortUrlDto createShortUrl(UrlRequestDto urlRequestDto) {
        String longUrl = urlRequestDto.getLongUrl();
        String shortUrl;

        while (true) {
            shortUrl = shortUrlGenerator.generate();
            try {
                urlService.getByShortUrl(shortUrl);
            } catch (EntityNotFoundException e) {
                break;
            }
        }

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

}
