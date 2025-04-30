package com.moldavets.url_shortener_api.exception;

public class LinkExpiredException extends RuntimeException {
    public LinkExpiredException(String message) {
        super(message);
    }
}
