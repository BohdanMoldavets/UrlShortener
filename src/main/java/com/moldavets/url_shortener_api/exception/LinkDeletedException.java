package com.moldavets.url_shortener_api.exception;

public class LinkDeletedException extends RuntimeException {
    public LinkDeletedException(String message) {
        super(message);
    }
}
