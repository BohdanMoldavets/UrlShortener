package com.moldavets.url_shortener_api.CacheServiceImplTest;

import com.moldavets.url_shortener_api.service.Impl.CacheServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CacheServiceImplTest {

    @InjectMocks
    CacheServiceImpl cacheServiceImpl;

    @Mock
    RedisTemplate<String, String> redisTemplate;

    @Mock
    ValueOperations<String, String> valueOperations;

    private final String shortUrl = "test";
    private final String longUrl = "http://example.com";


    @Test
    void getByShortUrl_shouldReturnLongUrl_whenInputContainsSoredShortUrl() {
        when(redisTemplate.opsForValue()).thenReturn(valueOperations);
        when(valueOperations.get(shortUrl)).thenReturn(longUrl);

        String resultLongUrl = cacheServiceImpl.getByShortUrl(shortUrl);

        assertEquals(longUrl, resultLongUrl);
        verify(valueOperations, Mockito.times(1)).get(shortUrl);
    }

    @Test
    void getByShortUrl_shouldThrowException_whenInputContainsNull() {
        assertThrows(NullPointerException.class,
                () -> cacheServiceImpl.getByShortUrl(null));

        verify(valueOperations, never()).get(shortUrl);
    }

    @Test
    void getByShortUrl_shouldThrowException_whenInputContainsStringWithOnlySpaces() {
        assertThrows(NullPointerException.class,
                () -> cacheServiceImpl.getByShortUrl("  "));

        verify(valueOperations, never()).get(shortUrl);
    }

    @Test
    void getByShortUrl_shouldThrowException_whenInputContainsEmptyString() {
        assertThrows(NullPointerException.class,
                () -> cacheServiceImpl.getByShortUrl(""));

        verify(valueOperations, never()).get(shortUrl);
    }

    @Test
    void save_shouldSaveLongUrlAndReturnShortUrl_whenInputContainsValidData() {
        when(redisTemplate.opsForValue()).thenReturn(valueOperations);
        doNothing().when(valueOperations).set(anyString(), anyString(), anyLong(), any(TimeUnit.class));

        String result = cacheServiceImpl.save(longUrl, shortUrl);

        assertEquals(shortUrl, result);
        verify(valueOperations, Mockito.times(1)).set(anyString(), anyString(), anyLong(), any(TimeUnit.class));
    }

    @Test
    void save_shouldThrowException_whenInputContainsNull() {
        assertThrows(NullPointerException.class,
                () -> cacheServiceImpl.save(null, null));

        verify(valueOperations, never()).set(anyString(), anyString(), anyInt(), any(TimeUnit.class));
    }

    @Test
    void save_shouldThrowException_whenInputContainsStringWithOnlySpaces() {
        assertThrows(NullPointerException.class,
                () -> cacheServiceImpl.save("  ", "  "));

        verify(valueOperations, never()).set(anyString(), anyString(), anyInt(), any(TimeUnit.class));
    }

    @Test
    void save_shouldThrowException_whenInputContainsEmptyString() {
        assertThrows(NullPointerException.class,
                () -> cacheServiceImpl.save("", ""));

        verify(valueOperations, never()).set(anyString(), anyString(), anyInt(), any(TimeUnit.class));
    }

    @Test
    void deleteByShortUrl_shouldDeleteFromCache_whenInputContainsValidData() {
        when(redisTemplate.delete(shortUrl)).thenReturn(true);

        cacheServiceImpl.deleteByShortUrl(shortUrl);

        verify(redisTemplate, Mockito.times(1)).delete(shortUrl);
    }

    @Test
    void deleteByShortUrl_shouldThrowException_whenInputContainsNull() {
        assertThrows(NullPointerException.class,
                () -> cacheServiceImpl.deleteByShortUrl(null));

        verify(redisTemplate, never()).delete(shortUrl);
    }

    @Test
    void deleteByShortUrl_shouldThrowException_whenInputContainsStringWithOnlySpaces() {
        assertThrows(NullPointerException.class,
                () -> cacheServiceImpl.deleteByShortUrl("  "));

        verify(redisTemplate, never()).delete(shortUrl);
    }

    @Test
    void deleteByShortUrl_shouldThrowException_whenInputContainsEmptyString() {
        assertThrows(NullPointerException.class,
                () -> cacheServiceImpl.deleteByShortUrl(""));

        verify(redisTemplate, never()).delete(shortUrl);
    }
}