package com.deepanshu.snaplink.exception;

import com.deepanshu.snaplink.dto.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AssertionError.class)
    public ResponseDto handleAssertionError(AssertionError ex){
        return ResponseDto.builder().message(ex.getMessage()).statusCode(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @ExceptionHandler(DuplicateURLException.class)
    public ResponseDto handleDuplicateURLException(DuplicateURLException ex){
        return ResponseDto.builder().message(ex.getMessage()).statusCode(HttpStatus.CONFLICT).build();
    }
    @ExceptionHandler(SnapLinkException.class)
    public ResponseDto handleSnapLinkException(SnapLinkException ex){
        return ResponseDto.builder()
                .message(ex.getMessage())
                .success(ex.isSuccess())
                .data(ex.getData())
                .statusCode(ex.getStatusCode()).build();
    }
}
