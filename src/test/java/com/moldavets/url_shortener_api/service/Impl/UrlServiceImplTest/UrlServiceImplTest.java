package com.moldavets.url_shortener_api.service.Impl.UrlServiceImplTest;

import com.moldavets.url_shortener_api.model.entity.Impl.url.LinkStatus;
import com.moldavets.url_shortener_api.model.entity.Impl.url.Url;
import com.moldavets.url_shortener_api.repository.UrlRepository;
import com.moldavets.url_shortener_api.service.Impl.UrlServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UrlServiceImplTest {

    @InjectMocks
    private UrlServiceImpl urlServiceImpl;

    @Mock
    private UrlRepository urlRepository;

    private String longUrl = "htt://example.com/test";
    private String shortUrl = "PnGyot";
    private Url url = new Url(
            1L,
            longUrl,
            shortUrl,
            Instant.now().plusSeconds(2628000),
            LinkStatus.ACTIVE,
            0L
    );


    @Test
    void save_shouldSaveEntityToDatabase_whenInputContainsValidData() {
        when(urlRepository.save(any(Url.class))).thenReturn(url);

        Url actual = urlServiceImpl.save(longUrl, shortUrl);

        assertEquals(longUrl, actual.getLongUrl());
        assertEquals(shortUrl, actual.getShortUrl());
        assertEquals(url.getId(), actual.getId());
        assertEquals(url.getExpiresDate(), actual.getExpiresDate());
        assertEquals(url.getLinkStatus(), actual.getLinkStatus());
        assertEquals(url.getTotalClicks(), actual.getTotalClicks());

        verify(urlRepository, Mockito.times(1)).save(any(Url.class));
    }

    @Test
    void save_shouldThrowException_whenInputContainsNull() {
        assertThrows(NullPointerException.class,
                () -> urlServiceImpl.save(null, null));

        verify(urlRepository, never()).save(any(Url.class));
    }

    @Test
    void save_shouldThrowException_whenInputContainsStringOnlyWithSpaces() {
        assertThrows(NullPointerException.class,
                () -> urlServiceImpl.save("  ", "  "));

        verify(urlRepository, never()).save(any(Url.class));
    }

    @Test
    void save_shouldThrowException_whenInputContainsEmptyString() {
        assertThrows(NullPointerException.class,
                () -> urlServiceImpl.save("", ""));

        verify(urlRepository, never()).save(any(Url.class));
    }

    @Test
    void getByShortUrl_shouldReturnUrl_whenInputContainsStoredShortUrl() {
        when(urlRepository.findByShortUrl(shortUrl)).thenReturn(Optional.of(url));

        Url actual = urlServiceImpl.getByShortUrl(shortUrl);

        assertEquals(shortUrl, actual.getShortUrl());
        assertEquals(url.getLongUrl(), actual.getLongUrl());
        assertEquals(url.getId(), actual.getId());
        assertEquals(url.getExpiresDate(), actual.getExpiresDate());
        assertEquals(url.getLinkStatus(), actual.getLinkStatus());
        assertEquals(url.getTotalClicks(), actual.getTotalClicks());

        verify(urlRepository, Mockito.times(1)).findByShortUrl(shortUrl);
    }

    @Test
    void getByShortUrl_shouldThrowException_whenShortUrlDoesNotExist() {
        when(urlRepository.findByShortUrl(shortUrl)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> urlServiceImpl.getByShortUrl(shortUrl));

        verify(urlRepository, Mockito.times(1)).findByShortUrl(shortUrl);
    }

    @Test
    void getByShortUrl_shouldThrowException_whenInputContainsNull() {
        assertThrows(NullPointerException.class,
                () -> urlServiceImpl.getByShortUrl(null));

        verify(urlRepository, never()).findByShortUrl(anyString());
    }

    @Test
    void getByShortUrl_shouldThrowException_whenInputContainsStringOnlyWithSpaces() {
        assertThrows(NullPointerException.class,
                () -> urlServiceImpl.getByShortUrl("  "));

        verify(urlRepository, never()).findByShortUrl(anyString());
    }

    @Test
    void getByShortUrl_shouldThrowException_whenInputContainsEmptyString() {
        assertThrows(NullPointerException.class,
                () -> urlServiceImpl.getByShortUrl(""));

        verify(urlRepository, never()).findByShortUrl(anyString());
    }

    @Test
    void updateUrlStatusById_shouldUpdateUrlStatus_whenInputContainsValidData() {
        LinkStatus status = LinkStatus.EXPIRED;
        Long id = 1L;

        urlServiceImpl.updateUrlStatusById(status, id);

        ArgumentCaptor<Instant> instantCaptor = ArgumentCaptor.forClass(Instant.class);

        verify(urlRepository).updateUrlStatusById(eq(status), instantCaptor.capture(), eq(id));

        assertNotNull(instantCaptor.getValue());
    }

    @Test
    void updateUrlStatusById_shouldThrowException_whenInputContainsNull() {
        assertThrows(NullPointerException.class,
                () -> urlServiceImpl.updateUrlStatusById(null, null));

        verify(urlRepository, never()).updateUrlStatusById(any(LinkStatus.class), any(Instant.class), anyLong());
    }

    @Test
    void incrementUrlClicksByShortUrl_shouldIncrementUrlClicks_whenInputContainsValidData() {
        urlServiceImpl.incrementUrlClicksByShortUrl(shortUrl);

        ArgumentCaptor<Instant> instantCaptor = ArgumentCaptor.forClass(Instant.class);

        verify(urlRepository).incrementUrlClicksByShortUrl(eq(shortUrl), instantCaptor.capture());

        assertNotNull(instantCaptor.getValue());
    }

    @Test
    void incrementUrlClicksByShortUrl_shouldThrowException_whenInputContainsStringOnlyWithSpaces() {
        assertThrows(NullPointerException.class,
                () -> urlServiceImpl.incrementUrlClicksByShortUrl("  "));

        verify(urlRepository, never()).incrementUrlClicksByShortUrl(anyString(), any(Instant.class));
    }

    @Test
    void incrementUrlClicksByShortUrl_shouldThrowException_whenInputContainsEmptyString() {
        assertThrows(NullPointerException.class,
                () -> urlServiceImpl.incrementUrlClicksByShortUrl("  "));

        verify(urlRepository, never()).incrementUrlClicksByShortUrl(anyString(), any(Instant.class));
    }

    @Test
    void incrementUrlClicksByShortUrl_shouldThrowException_whenInputContainsNull() {
        assertThrows(NullPointerException.class,
                () -> urlServiceImpl.incrementUrlClicksByShortUrl(null));

        verify(urlRepository, never()).incrementUrlClicksByShortUrl(anyString(), any(Instant.class));
    }
}