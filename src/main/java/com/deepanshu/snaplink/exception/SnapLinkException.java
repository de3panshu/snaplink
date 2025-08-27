package com.deepanshu.snaplink.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class SnapLinkException extends Exception{
    private final HttpStatus statusCode;
    private final boolean success;
    private final Object data;

    public SnapLinkException(String message,HttpStatus statusCode,boolean success, Object data){
        super(message);
        this.data = data;
        this.statusCode = statusCode;
        this.success = success;
    }
}
