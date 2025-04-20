package com.moldavets.url_shortener_api.repository;

import com.moldavets.url_shortener_api.model.entity.Impl.Url;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UrlRepository extends CrudRepository<Url, Long> {
    Optional<Url> findById(long id);
    Optional<Url> findByLongUrl(String longUrl);
    Optional<Url> findByShortUrl(String shortUrl);
    boolean existsByLongUrl(String longUrl);
}
