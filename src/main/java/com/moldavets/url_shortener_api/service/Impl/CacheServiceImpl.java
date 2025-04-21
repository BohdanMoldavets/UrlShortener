package com.moldavets.url_shortener_api.service.Impl;

import com.moldavets.url_shortener_api.service.CacheService;
import com.moldavets.url_shortener_api.service.Saveable;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class CacheServiceImpl implements CacheService, Saveable<String> {

    private final RedisTemplate<String, String> redisTemplate;

    @Override
    public String getByShortUrl(String shortUrl) {
        return redisTemplate.opsForValue().get(shortUrl);
    }

    @Override
    public String save(String longUrl, String shortUrl) {
        redisTemplate.opsForValue().set(shortUrl, longUrl, 1440, TimeUnit.MINUTES);
        return shortUrl;
    }

    @Override
    public void deleteByShortUrl(String shortUrl) {
        redisTemplate.delete(shortUrl);
    }
}
