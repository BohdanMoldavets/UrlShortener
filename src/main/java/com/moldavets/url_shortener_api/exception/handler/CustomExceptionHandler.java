package com.moldavets.url_shortener_api.exception.handler;

import com.moldavets.url_shortener_api.exception.model.ExceptionDetailsModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.Objects;

@RestControllerAdvice
public class CustomExceptionHandler {

    private static final String VALIDATION_POSSIBLE_ERRORS = "Check if the domain is correct. " +
            "Check if the site is online. " +
            "Check the address bars and punctuation. " +
            "The URL may be being used for spam. " +
            "The URL may have been blocked. " +
            "The URL may have been reported. " +
            "The URL was recently shortened. " +
            "The URL is not allowed. " +
            "You shortened many URLs in a short time.";

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionDetailsModel> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, WebRequest request) {
        return new ResponseEntity<>(new ExceptionDetailsModel(
                LocalDateTime.now(),
                "An error occurred creating the short URL. The URL has not been shortened, possible errors: " + VALIDATION_POSSIBLE_ERRORS,
                request.getDescription(false)
        ), HttpStatus.BAD_REQUEST);
    }

    private ExceptionDetailsModel createExceptionDetailsModel(Exception ex, WebRequest request) {
        return new ExceptionDetailsModel(
                LocalDateTime.now(),
                ex.getMessage(),
                request.getDescription(false)
        );
    }

}
