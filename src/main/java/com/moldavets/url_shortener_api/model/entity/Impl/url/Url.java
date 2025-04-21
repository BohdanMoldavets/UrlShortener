package com.moldavets.url_shortener_api.model.entity.Impl.url;

import com.moldavets.url_shortener_api.model.entity.AbstractAuditingEntity;
import jakarta.persistence.*;
import lombok.Builder;


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

    @Column(name = "expires_date", nullable = false)
    private Instant expiresDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "link_status", nullable = false)
    private LinkStatus linkStatus;

    public Url() {
    }

    public Url(String longUrl, String shortUrl, Instant expiresDate) {
        this.longUrl = longUrl;
        this.shortUrl = shortUrl;
        this.expiresDate = expiresDate;
        this.linkStatus = LinkStatus.ACTIVE;
    }

    public Url(Long id, String longUrl, String shortUrl, Instant expiresDate, LinkStatus linkStatus) {
        this.id = id;
        this.longUrl = longUrl;
        this.shortUrl = shortUrl;
        this.expiresDate = expiresDate;
        this.linkStatus = linkStatus;
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

    public LinkStatus getLinkStatus() {
        return linkStatus;
    }

    public void setLinkStatus(LinkStatus linkStatus) {
        this.linkStatus = linkStatus;
    }
}
