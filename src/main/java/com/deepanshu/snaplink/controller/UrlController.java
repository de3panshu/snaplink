package com.deepanshu.snaplink.controller;

import com.deepanshu.snaplink.dto.ResponseDto;
import com.deepanshu.snaplink.dto.urlDto.ShortUrlCreateRequestDto;
import com.deepanshu.snaplink.dto.urlDto.ShortUrlCreateResponseDto;
import com.deepanshu.snaplink.service.ShortUrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.Optional;


@RestController
public class UrlController{
    @Autowired
    private ShortUrlService urlService;

    @PostMapping("/shorten")
    public ResponseDto addNewUrl(@RequestBody ShortUrlCreateRequestDto urlInfo){
        Optional<ShortUrlCreateResponseDto> result = urlService.addShortUrl(urlInfo);
        return ResponseDto.builder()
                .statusCode(result.isPresent()? HttpStatus.CREATED:HttpStatus.INTERNAL_SERVER_ERROR)
                .message(result.isPresent() ? "Url shorted completed.":"")
                .data(result)
                .build();
    }
}
