package com.moldavets.url_shortener_api.service.Impl;

import com.moldavets.url_shortener_api.model.entity.Impl.url.LinkStatus;
import com.moldavets.url_shortener_api.model.entity.Impl.url.Url;
import com.moldavets.url_shortener_api.repository.UrlRepository;
import com.moldavets.url_shortener_api.service.Saveable;
import com.moldavets.url_shortener_api.service.UrlService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Slf4j
@Service
@RequiredArgsConstructor
public class UrlServiceImpl implements UrlService, Saveable<Url> {

    private final UrlRepository urlRepository;

    @Override
    @Transactional
    public Url save(String longUrl, String shortUrl) {
        if(longUrl == null || shortUrl == null || longUrl.trim().isEmpty() || shortUrl.trim().isEmpty()) {
            throw new NullPointerException("Input data cannot be null");
        }
        Url storedUrl = urlRepository.save(new Url(longUrl, shortUrl, Instant.now().plusSeconds(2628000)));
        log.info("Saved in database: {}", storedUrl);
        return storedUrl;
    }

    @Override
    @Transactional(readOnly = true)
    public Url getByShortUrl(String shortUrl) {
        if(shortUrl == null || shortUrl.trim().isEmpty()) {
            throw new NullPointerException("Input data cannot be null");
        }
        Url retrievedUrl = urlRepository.findByShortUrl(shortUrl)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Entity with url - [%s] does not exist", shortUrl)));
        log.info("Retrieved from database: {}", retrievedUrl);
        return retrievedUrl;
    }

    @Override
    @Transactional
    public void updateUrlStatusById(LinkStatus linkStatus, Long id) {
        if(linkStatus == null || id == null) {
            throw new NullPointerException("Input data cannot be null");
        }
        urlRepository.updateUrlStatusById(linkStatus, Instant.now(), id);
        log.info("Status changed to: {}, for Url with id: {}", linkStatus, id);
    }

    @Override
    @Transactional
    public void incrementUrlClicksByShortUrl(String shortUrl) {
        if(shortUrl == null || shortUrl.trim().isEmpty()) {
            throw new NullPointerException("Input data cannot be null");
        }
        urlRepository.incrementUrlClicksByShortUrl(shortUrl, Instant.now());
        log.info("Incremented url clicks by short url: {}", shortUrl);
    }
}
