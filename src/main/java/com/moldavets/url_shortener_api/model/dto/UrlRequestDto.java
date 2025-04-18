package com.moldavets.url_shortener_api.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UrlRequestDto {
    @JsonProperty(value = "long_url")
    private String longUrl;
}
