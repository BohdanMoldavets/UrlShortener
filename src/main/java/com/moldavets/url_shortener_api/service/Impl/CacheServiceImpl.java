package com.moldavets.url_shortener_api.service.Impl;

import com.moldavets.url_shortener_api.model.entity.Impl.Url;
import com.moldavets.url_shortener_api.service.CacheService;
import com.moldavets.url_shortener_api.service.Saveable;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CacheServiceImpl implements CacheService, Saveable<String> {

    private final RedisTemplate<String, String> redisTemplate;

    @Override
    public String getByShortUrl(String shortUrl) {
        return "";
    }

    @Override
    public String save(String longUrl, String shortUrl) {
        return null;
    }
}
