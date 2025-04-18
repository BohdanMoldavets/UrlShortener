package com.moldavets.url_shortener_api.service;

import com.moldavets.url_shortener_api.model.dto.UrlRequestDto;
import com.moldavets.url_shortener_api.model.entity.Impl.Url;

public interface UrlService {
    Url save(UrlRequestDto urlRequestDto);
}
