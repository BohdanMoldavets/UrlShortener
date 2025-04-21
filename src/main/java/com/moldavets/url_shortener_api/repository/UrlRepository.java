package com.moldavets.url_shortener_api.repository;

import com.moldavets.url_shortener_api.model.entity.Impl.url.LinkStatus;
import com.moldavets.url_shortener_api.model.entity.Impl.url.Url;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UrlRepository extends CrudRepository<Url, Long> {
    Optional<Url> findById(long id);
    Optional<Url> findByLongUrl(String longUrl);
    Optional<Url> findByShortUrl(String shortUrl);
    boolean existsByLongUrl(String longUrl);

    @Modifying
    @Query("update Url u set u.linkStatus= :linkStatus where u.id = :id")
    void updateUrlStatusById(LinkStatus linkStatus, Long id);

    @Modifying
    @Query("update Url u set u.totalClicks = u.totalClicks + 1 where u.shortUrl= :shortUrl")
    void incrementUrlClicksByShortUrl(String shortUrl);
}
