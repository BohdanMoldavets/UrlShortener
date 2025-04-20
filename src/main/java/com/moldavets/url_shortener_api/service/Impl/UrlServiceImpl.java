package com.moldavets.url_shortener_api.service.Impl;

import com.moldavets.url_shortener_api.exception.EntityExistsException;
import com.moldavets.url_shortener_api.model.entity.Impl.Url;
import com.moldavets.url_shortener_api.repository.UrlRepository;
import com.moldavets.url_shortener_api.service.Saveable;
import com.moldavets.url_shortener_api.service.UrlService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class UrlServiceImpl implements UrlService, Saveable<Url> {

    private final UrlRepository urlRepository;

    @Override
    @Transactional
    public Url save(String longUrl, String shortUrl) {
        if(!urlRepository.existsByLongUrl(longUrl)) {
            return urlRepository.save(new Url(longUrl, shortUrl, Instant.now().plusSeconds(3600)));
        }
        throw new EntityExistsException(String.format("Entity with url - [%s] already exists", longUrl));
    }

    @Override
    @Transactional(readOnly = true)
    public Url getByShortUrl(String shortUrl) {
        return urlRepository.findByShortUrl(shortUrl)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Entity with url - [%s] does not exist", shortUrl)));
    }
}
