package com.moldavets.url_shortener_api.exception.handler;

import com.moldavets.url_shortener_api.exception.LinkExpiredException;
import com.moldavets.url_shortener_api.exception.model.ExceptionDetailsModel;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@Slf4j
@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionDetailsModel> handleAllExceptions(Exception ex, WebRequest request) {
        log.error(ex.getMessage(), ex);
        return new ResponseEntity<>(new ExceptionDetailsModel(
                LocalDateTime.now(),
                "An unexpected error occurred. Please try again later",
                null
        ), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(LinkExpiredException.class)
    public ResponseEntity<ExceptionDetailsModel> handleLinkExpiredException(LinkExpiredException ex, WebRequest request) {
        log.warn(ex.getMessage(), ex);
        return new ResponseEntity<>(createExceptionDetailsModel(ex.getMessage(), request), HttpStatus.GONE);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ExceptionDetailsModel> handleEntityNotFoundException(EntityNotFoundException ex, WebRequest request) {
        log.warn(ex.getMessage(), ex);
        return new ResponseEntity<>(createExceptionDetailsModel("Link not found", request), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionDetailsModel> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, WebRequest request) {
        log.warn(ex.getMessage(), ex);
        return new ResponseEntity<>(createExceptionDetailsModel("Invalid link", request), HttpStatus.BAD_REQUEST);
    }

    private ExceptionDetailsModel createExceptionDetailsModel(String message, WebRequest request) {
        return new ExceptionDetailsModel(
                LocalDateTime.now(),
                message,
                request.getDescription(false)
        );
    }

}
