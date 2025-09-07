package com.deepanshu.snaplink.controller;

import com.deepanshu.snaplink.dto.ResponseDto;
import com.deepanshu.snaplink.dto.urlDto.ShortUrlCreateRequestDto;
import com.deepanshu.snaplink.entity.OriginalUrl;
import com.deepanshu.snaplink.entity.ShortUrl;
import com.deepanshu.snaplink.service.ShortUrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;


@RestController
@RequestMapping("/api/url")
public class UrlController{
    @Autowired
    private ShortUrlService urlService;

    @PostMapping("/shorten")
    public ResponseEntity<Map<String,Object>> addNewUrl(@RequestBody ShortUrlCreateRequestDto urlInfo){
        Optional<ShortUrl> result = urlService.addShortUrl(urlInfo);
        return ResponseDto.builder()
                .statusCode(result.isPresent()? HttpStatus.CREATED:HttpStatus.INTERNAL_SERVER_ERROR)
                .message(result.isPresent() ? "Url shortening completed.":"")
                .data(result)
                .success(result.isPresent())
                .build()
                .getResponse();
    }

    @PatchMapping("/geo")
    public ResponseEntity<Map<String,Object>> addGeoUrls(@RequestBody ShortUrlCreateRequestDto urlInfo){
        Optional<OriginalUrl> result = urlService.addOriginalUrl(urlInfo);
        return ResponseDto.builder()
                .statusCode(result.isPresent()? HttpStatus.CREATED:HttpStatus.INTERNAL_SERVER_ERROR)
                .message(result.isPresent() ? "Geo url added completed.":"")
                .data(result)
                .success(result.isPresent())
                .build()
                .getResponse();
    }
}
