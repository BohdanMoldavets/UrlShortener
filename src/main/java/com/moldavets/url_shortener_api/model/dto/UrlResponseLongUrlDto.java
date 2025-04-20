package com.moldavets.url_shortener_api.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UrlResponseLongUrlDto {
    @JsonProperty(value = "long_url")
    private String longUrl;

    public UrlResponseLongUrlDto(String longUrl) {
        this.longUrl = longUrl;
    }
}
