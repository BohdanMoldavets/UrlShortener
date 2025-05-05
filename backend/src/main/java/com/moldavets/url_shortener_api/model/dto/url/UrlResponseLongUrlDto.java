package com.moldavets.url_shortener_api.model.dto.url;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.net.URI;

public class UrlResponseLongUrlDto {
    @JsonProperty(value = "long_url")
    private URI longUrl;

    public UrlResponseLongUrlDto(URI longUrl) {
        this.longUrl = longUrl;
    }
}
