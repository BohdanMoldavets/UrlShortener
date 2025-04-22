package com.moldavets.url_shortener_api.service.Impl;

import com.moldavets.url_shortener_api.model.entity.Impl.url.LinkStatus;
import com.moldavets.url_shortener_api.model.entity.Impl.url.Url;
import com.moldavets.url_shortener_api.repository.UrlRepository;
import com.moldavets.url_shortener_api.service.Saveable;
import com.moldavets.url_shortener_api.service.UrlService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class UrlServiceImpl implements UrlService, Saveable<Url> {

    private final UrlRepository urlRepository;

    @Override
    @Transactional
    public Url save(String longUrl, String shortUrl) {
        return urlRepository.save(new Url(longUrl, shortUrl, Instant.now().plusSeconds(2628000)));
    }

    @Override
    @Transactional(readOnly = true)
    public Url getByShortUrl(String shortUrl) {
        return urlRepository.findByShortUrl(shortUrl)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Entity with url - [%s] does not exist", shortUrl)));
    }

    @Override
    @Transactional
    public void updateUrlStatusById(LinkStatus linkStatus, Long id) {
        urlRepository.updateUrlStatusById(linkStatus, Instant.now(), id);
    }

    @Override
    @Transactional
    public void incrementUrlClicksByShortUrl(String shortUrl) {
        urlRepository.incrementUrlClicksByShortUrl(shortUrl, Instant.now());
    }
}
