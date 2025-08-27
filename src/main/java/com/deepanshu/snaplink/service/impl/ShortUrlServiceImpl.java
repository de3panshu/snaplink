package com.deepanshu.snaplink.service.impl;

import com.deepanshu.snaplink.dto.urlDto.ShortUrlCreateRequestDto;
import com.deepanshu.snaplink.entity.OriginalUrl;
import com.deepanshu.snaplink.entity.ShortUrl;
import com.deepanshu.snaplink.exception.DuplicateURLException;
import com.deepanshu.snaplink.exception.SnapLinkException;
import com.deepanshu.snaplink.repo.OriginalUrlRepo;
import com.deepanshu.snaplink.repo.ShortUrlRepo;
import com.deepanshu.snaplink.service.ShortUrlService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ShortUrlServiceImpl implements ShortUrlService {
    @Autowired
    private ShortUrlRepo shortUrlRepo;

    @Autowired
    private OriginalUrlRepo originalUrlRepo;

    @Override
    public Optional<ShortUrl> addShortUrl(ShortUrlCreateRequestDto shortUrl){
        ShortUrl toBeAddShortUrl  = null;
        if(shortUrl.getSid() == -1){
            toBeAddShortUrl = ShortUrl
                    .builder()
                    .shortUrl(shortUrl.getShortUrl())
                    .originalUrls(List.of(OriginalUrl.builder()
                            .metaTitle(shortUrl.getTitle())
                            .metaDescription(shortUrl.getDescription())
                            .build()))
                    .build();
            toBeAddShortUrl = shortUrlRepo.save(toBeAddShortUrl);
        }
        return Optional.of(toBeAddShortUrl);
    }

    @SneakyThrows
    @Override
    public Optional<OriginalUrl> addOriginalUrl(ShortUrlCreateRequestDto shortUrlDto){
        OriginalUrl originalUrl = null;
        if(shortUrlDto.getSid() != -1){//adding only the Original URL correspond to the given short URL Id
            ShortUrl shortUrl = shortUrlRepo.findById(shortUrlDto.getSid())
                    .orElseThrow(()->new SnapLinkException(
                            String.format("No Short URL found with Id: %d",shortUrlDto.getSid()),
                            HttpStatus.UNPROCESSABLE_ENTITY,
                            false,
                            null)
                    );
            originalUrl = originalUrlRepo.save(OriginalUrl.builder()
                            .url(shortUrlDto.getOriginalUrl().toString())
                            .shortUrl(shortUrl)
                            .metaDescription(shortUrlDto.getDescription())
                            .metaTitle(shortUrlDto.getTitle())
                            .build());
        }
        else{//adding the new short url and original url both
            throw new SnapLinkException(
                    String.format("Invalid Id: %d to update record.",shortUrlDto.getSid()),//updating the short url by making new entry into the original url.
                    HttpStatus.UNPROCESSABLE_ENTITY,
                    false,
                    null);
        }
        return Optional.of(originalUrl);
    }
}
