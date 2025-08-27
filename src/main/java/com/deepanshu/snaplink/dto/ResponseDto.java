package com.deepanshu.snaplink.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@Builder
public class ResponseDto {
    private HttpStatus statusCode;
    private String message;
    private boolean success;
    private Object data;
}
