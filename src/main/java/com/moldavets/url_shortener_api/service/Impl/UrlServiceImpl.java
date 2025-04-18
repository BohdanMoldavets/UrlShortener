package com.moldavets.url_shortener_api.service.Impl;

import com.moldavets.url_shortener_api.model.dto.UrlRequestDto;
import com.moldavets.url_shortener_api.model.entity.Impl.Url;
import com.moldavets.url_shortener_api.repository.UrlRepository;
import com.moldavets.url_shortener_api.service.UrlService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UrlServiceImpl implements UrlService {

    private final UrlRepository urlRepository;

    @Override
    public Url save(UrlRequestDto urlRequestDto) {
        if(!urlRepository.existsByLongUrl(urlRequestDto.getLongUrl())) {
            return urlRepository.save(new Url(urlRequestDto.getLongUrl()));
        }
        throw new
    }
}
