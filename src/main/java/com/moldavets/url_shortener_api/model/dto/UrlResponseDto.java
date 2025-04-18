package com.moldavets.url_shortener_api.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UrlResponseDto {
    @JsonProperty(value = "short_url")
    private String shortUrl;

    public UrlResponseDto(String shortUrl) {
        this.shortUrl = shortUrl;
    }
}
