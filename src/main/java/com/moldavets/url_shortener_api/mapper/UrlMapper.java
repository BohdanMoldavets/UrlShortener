package com.moldavets.url_shortener_api.mapper;

import com.moldavets.url_shortener_api.model.dto.UrlResponseDto;
import com.moldavets.url_shortener_api.model.entity.Impl.Url;

public final class UrlMapper {

    private UrlMapper() {}

    public static UrlResponseDto to(Url url) {
        return new UrlResponseDto(url.getShortUrl());
    }

//    public static Url from(UrlRequestDto urlRequestDto) {
//
//    }
}
