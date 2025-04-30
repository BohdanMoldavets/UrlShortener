package com.moldavets.url_shortener_api.service;

public interface Saveable <T> {
    T save(String longUrl, String shortUrl);
}
