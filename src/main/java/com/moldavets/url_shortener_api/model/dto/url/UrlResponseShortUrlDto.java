package com.moldavets.url_shortener_api.model.dto.url;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UrlResponseShortUrlDto {
    @JsonProperty(value = "short_url")
    private String shortUrl;

    public UrlResponseShortUrlDto(String shortUrl) {
        this.shortUrl = shortUrl;
    }
}
