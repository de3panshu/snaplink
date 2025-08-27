package com.deepanshu.snaplink.service;

import com.deepanshu.snaplink.dto.urlDto.ShortUrlCreateRequestDto;
import com.deepanshu.snaplink.entity.OriginalUrl;
import com.deepanshu.snaplink.entity.ShortUrl;
import java.util.Optional;

public interface ShortUrlService {
    Optional<ShortUrl> addShortUrl(ShortUrlCreateRequestDto shortUrl);
    Optional<OriginalUrl> addOriginalUrl(ShortUrlCreateRequestDto shortUrl);
}
