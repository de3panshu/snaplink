package com.deepanshu.snaplink.service;

import com.deepanshu.snaplink.dto.urlDto.ShortUrlCreateRequestDto;
import com.deepanshu.snaplink.dto.urlDto.ShortUrlCreateResponseDto;

import java.util.Optional;

public interface ShortUrlService {
    Optional<ShortUrlCreateResponseDto> addShortUrl(ShortUrlCreateRequestDto shortUrl);
}
