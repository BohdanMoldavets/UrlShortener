package com.moldavets.url_shortener_api.service;

import com.moldavets.url_shortener_api.model.entity.Impl.url.LinkStatus;
import com.moldavets.url_shortener_api.model.entity.Impl.url.Url;

public interface UrlService extends Service<Url>{
    void updateUrlStatusById(LinkStatus linkStatus, Long id);
    void incrementUrlClicksByShortUrl(String shortUrl);
}
