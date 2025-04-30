package com.moldavets.url_shortener_api.service;

public interface Service<T> {
    T getByShortUrl(String shortUrl);
}
