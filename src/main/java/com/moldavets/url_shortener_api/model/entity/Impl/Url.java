package com.moldavets.url_shortener_api.model.entity.Impl;

import com.moldavets.url_shortener_api.model.entity.AbstractAuditingEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@Table(name = "url")
@Builder
public class Url extends AbstractAuditingEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //TODO - SEQUENCE AFTER MIGRATION
    @Column(name = "id")
    private Long id;

    @Column(name = "long_url", nullable = false)
    private String longUrl;

    @Column(name = "short_url", nullable = false)
    private String shortUrl;

    private Instant expiresDate;

    public Url() {
    }

    public Url(String longUrl) {
        this.longUrl = longUrl;
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLongUrl() {
        return longUrl;
    }

    public void setLongUrl(String longUrl) {
        this.longUrl = longUrl;
    }

    public String getShortUrl() {
        return shortUrl;
    }

    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }

    public Instant getExpiresDate() {
        return expiresDate;
    }

    public void setExpiresDate(Instant expiresDate) {
        this.expiresDate = expiresDate;
    }
}
