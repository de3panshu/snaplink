package com.deepanshu.snaplink.dto.urlDto;

import lombok.Builder;
import lombok.Data;

import java.net.URI;

@Data
@Builder
public class ShortUrlCreateResponseDto {
    private int sid;
    private URI shortUrl;
    private URI originalUrl;
    private String title;
    private String description;
}
