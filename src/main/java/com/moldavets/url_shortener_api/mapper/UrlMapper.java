package com.moldavets.url_shortener_api.mapper;

import com.moldavets.url_shortener_api.model.dto.UrlResponseShortUrlDto;
import com.moldavets.url_shortener_api.model.entity.Impl.Url;

public final class UrlMapper {

    private UrlMapper() {}

    public static UrlResponseShortUrlDto to(Url url) {
        return new UrlResponseShortUrlDto(url.getShortUrl());
    }

//    public static Url from(UrlRequestDto urlRequestDto) {
//
//    }
}
