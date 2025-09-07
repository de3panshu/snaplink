package com.deepanshu.snaplink.service.impl;

import com.deepanshu.snaplink.dto.urlDto.ShortUrlCreateRequestDto;
import com.deepanshu.snaplink.entity.OriginalUrl;
import com.deepanshu.snaplink.entity.ShortUrl;
import com.deepanshu.snaplink.exception.DuplicateURLException;
import com.deepanshu.snaplink.exception.SnapLinkException;
import com.deepanshu.snaplink.repo.OriginalUrlRepo;
import com.deepanshu.snaplink.repo.ShortUrlRepo;
import com.deepanshu.snaplink.service.ShortUrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

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
    public Optional<ShortUrl> addShortUrl(ShortUrlCreateRequestDto shortUrl) {
        ShortUrl toBeAddShortUrl = ShortUrl.builder()
                .shortUrl(generateShortUrl(shortUrl.getShortUrl(), shortUrl.getOriginalUrl().toString()))
                .build();

        OriginalUrl originalUrl = OriginalUrl.builder()
                .metaTitle(shortUrl.getTitle())
                .url(shortUrl.getOriginalUrl().toString())
                .metaDescription(shortUrl.getDescription())
                .shortUrl(toBeAddShortUrl)
                .build();

        toBeAddShortUrl.setOriginalUrls(List.of(originalUrl));

        toBeAddShortUrl = shortUrlRepo.save(toBeAddShortUrl);

        return Optional.of(toBeAddShortUrl);
    }

    @Override
    public Optional<OriginalUrl> addOriginalUrl(ShortUrlCreateRequestDto shortUrlDto){
        OriginalUrl originalUrl;
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

    private String generateShortUrl(String shortUrl,String originalUrl) throws DuplicateURLException {
        if(shortUrl == null || shortUrl.isBlank() || shortUrl.isEmpty()){
            shortUrl = UUID.randomUUID().toString();
        }
        else {
            if(isShortUrlPresentInDB(shortUrl)){
                throw new DuplicateURLException(shortUrl);
            }
        }
        return shortUrl;
    }
    private boolean isShortUrlPresentInDB(String url){//always looks for short URL bcoz original url can be duplicate by two different users but short url should be unique
        return false;
    }
}
