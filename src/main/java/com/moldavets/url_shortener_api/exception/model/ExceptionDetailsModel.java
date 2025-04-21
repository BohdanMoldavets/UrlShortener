package com.moldavets.url_shortener_api.exception.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
public class ExceptionDetailsModel {
    private LocalDateTime timestamp;
    private String message;
    private String details;
}
