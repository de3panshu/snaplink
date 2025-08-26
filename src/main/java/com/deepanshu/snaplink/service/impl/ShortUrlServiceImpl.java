package com.deepanshu.snaplink.service.impl;

import com.deepanshu.snaplink.dto.urlDto.ShortUrlCreateRequestDto;
import com.deepanshu.snaplink.dto.urlDto.ShortUrlCreateResponseDto;
import com.deepanshu.snaplink.entity.OriginalUrl;
import com.deepanshu.snaplink.entity.ShortUrl;
import com.deepanshu.snaplink.repo.OriginalUrlRepo;
import com.deepanshu.snaplink.repo.ShortUrlRepo;
import com.deepanshu.snaplink.service.ShortUrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class ShortUrlServiceImpl implements ShortUrlService {
    @Autowired
    private ShortUrlRepo shortUrlRepo;

    @Override
    public Optional<ShortUrlCreateResponseDto> addShortUrl(ShortUrlCreateRequestDto shortUrl){
        ShortUrlCreateResponseDto response = null;
        if(shortUrl.getSid() != -1){
            ShortUrl toBeAddUrlObject = ShortUrl
                    .builder()
                    .shortUrl(shortUrl.getShortUrl())
                    .originalUrls(List.of(OriginalUrl.builder()
                            .metaTitle(shortUrl.getTitle())
                            .metaDescription(shortUrl.getDescription())
                            .build()))
                    .build();
            toBeAddUrlObject = shortUrlRepo.save(toBeAddUrlObject);
            response = ShortUrlCreateResponseDto.builder()
                    .sid(toBeAddUrlObject.getSid())
                    .originalUrl(shortUrl.getOriginalUrl())
                    .build();
        }
        return Optional.of(response);
    }
}
