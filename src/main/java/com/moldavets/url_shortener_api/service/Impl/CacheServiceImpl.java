package com.moldavets.url_shortener_api.service.Impl;

import com.moldavets.url_shortener_api.service.CacheService;
import com.moldavets.url_shortener_api.service.Saveable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class CacheServiceImpl implements CacheService, Saveable<String> {

    private final RedisTemplate<String, String> redisTemplate;

    @Override
    public String getByShortUrl(String shortUrl) {
        String storedLongUrl = redisTemplate.opsForValue().get(shortUrl);
        log.info("Retrieved long url - [{}] by short url - [{}] from redis", storedLongUrl, shortUrl);
        return storedLongUrl;
    }

    @Override
    public String save(String longUrl, String shortUrl) {
        redisTemplate.opsForValue().set(shortUrl, longUrl, 1440, TimeUnit.MINUTES);
        log.info("Saved in redis: [{}:{}]", shortUrl, longUrl);
        return shortUrl;
    }

    @Override
    public void deleteByShortUrl(String shortUrl) {
        redisTemplate.delete(shortUrl);
        log.info("Deleted from redis: [{}]", shortUrl);
    }
}
