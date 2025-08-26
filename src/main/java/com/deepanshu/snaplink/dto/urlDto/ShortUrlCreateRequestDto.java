package com.deepanshu.snaplink.dto.urlDto;

import lombok.Data;

import java.net.URI;

@Data
public class ShortUrlCreateRequestDto {
    private int sid = -1;
    private String shortUrl;
    private URI originalUrl;
    private String title;
    private String description;
}
