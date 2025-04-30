package com.moldavets.url_shortener_api.controller;

import com.moldavets.url_shortener_api.exception.model.ExceptionDetailsModel;
import com.moldavets.url_shortener_api.model.dto.url.UrlRequestDto;
import com.moldavets.url_shortener_api.model.dto.url.UrlResponseInfoDto;
import com.moldavets.url_shortener_api.model.dto.url.UrlResponseLongUrlDto;
import com.moldavets.url_shortener_api.model.dto.url.UrlResponseShortUrlDto;
import com.moldavets.url_shortener_api.service.Impl.UrlApplicationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;


@Slf4j
@RestController
@RequestMapping("/api/v1/urls")
@RequiredArgsConstructor
@Tag(name = "Url controller", description = "Interaction with urls")
public class UrlController {

    private final UrlApplicationService urlApplicationService;

    @Operation(summary = "Redirect to original URL")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "301",
                    description = "Successfully redirected to original URL",
                    content = @Content(schema = @Schema(implementation = UrlResponseLongUrlDto.class), mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "410",
                    description = "The short URL has expired",
                    content = @Content(schema = @Schema(implementation = ExceptionDetailsModel.class), mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "The short URL was not found",
                    content = @Content(schema = @Schema(implementation = ExceptionDetailsModel.class), mediaType = "application/json")
            )
    })
    @GetMapping("/{shortUrl}")
    public ResponseEntity<Void> redirectLongUrl(@PathVariable("shortUrl") String shortUrl) {
        URI longUrl = urlApplicationService.getLongUrl(shortUrl);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(longUrl);
        log.info("Redirecting to - [{}]. Short url is - [{}]", longUrl, shortUrl);
        return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
    }

    @Operation(summary = "Retrieve info about short URL")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully retrieved info original about short URL",
                    content = @Content(schema = @Schema(implementation = UrlResponseInfoDto.class), mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "The short URL was not found",
                    content = @Content(schema = @Schema(implementation = ExceptionDetailsModel.class), mediaType = "application/json")
            )
    })
    @GetMapping("/info/{shortUrl}")
    public ResponseEntity<UrlResponseInfoDto> retrieveInfoOfShortUrl(@PathVariable("shortUrl") String shortUrl) {
        UrlResponseInfoDto infoByShortUrl = urlApplicationService.getInfoByShortUrl(shortUrl);
        log.info("Retrieving info about short url - [{}]", shortUrl);
        return new ResponseEntity<>(infoByShortUrl, HttpStatus.OK);
    }

    @Operation(summary = "Create a new short url")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "The short URL successfully created",
                    content = @Content(schema = @Schema(implementation = UrlResponseShortUrlDto.class), mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid input data",
                    content = @Content(schema = @Schema(implementation = ExceptionDetailsModel.class), mediaType = "application/json")
            )
    })
    @PostMapping
    public ResponseEntity<UrlResponseShortUrlDto> createShortUrl(@Valid @RequestBody UrlRequestDto urlRequestDto) {
        UrlResponseShortUrlDto createdShortUrl = urlApplicationService.createShortUrl(urlRequestDto);
        log.info("Short url - [{}], created for long url - [{}]", createdShortUrl.getShortUrl(), urlRequestDto.getLongUrl());
        return new ResponseEntity<>(createdShortUrl,HttpStatus.CREATED);
    }
}
