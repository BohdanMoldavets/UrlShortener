package com.moldavets.url_shortener_api.service;

import com.moldavets.url_shortener_api.model.entity.Impl.Url;

public interface Saveable <T> {
    T save(String longUrl, String shortUrl);
}
