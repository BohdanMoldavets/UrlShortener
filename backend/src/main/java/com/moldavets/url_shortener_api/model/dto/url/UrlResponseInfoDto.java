package com.moldavets.url_shortener_api.model.dto.url;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.moldavets.url_shortener_api.model.entity.Impl.url.LinkStatus;
import lombok.Data;


import java.time.Instant;

@Data
public class UrlResponseInfoDto {
    @JsonProperty(value = "long_url")
    private String longUrl;

    @JsonProperty(value = "short_url")
    private String shortUrl;

    @JsonProperty(value = "expires_date")
    private Instant expiresDate;

    @JsonProperty(value = "link_status")
    private LinkStatus linkStatus;

    @JsonProperty(value = "total_clicks")
    private Long totalClicks;

    public UrlResponseInfoDto(String longUrl, String shortUrl, Instant expiresDate, LinkStatus linkStatus, Long totalClicks) {
        this.longUrl = longUrl;
        this.shortUrl = shortUrl;
        this.expiresDate = expiresDate;
        this.linkStatus = linkStatus;
        this.totalClicks = totalClicks;
    }
}
