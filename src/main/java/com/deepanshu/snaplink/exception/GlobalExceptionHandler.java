package com.deepanshu.snaplink.exception;

import com.deepanshu.snaplink.dto.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AssertionError.class)
    public ResponseEntity<Map<String,Object>> handleAssertionError(AssertionError ex){
        return ResponseDto.builder().message(ex.getMessage()).statusCode(HttpStatus.INTERNAL_SERVER_ERROR).build().getResponse();
    }

    @ExceptionHandler(DuplicateURLException.class)
    public ResponseEntity<Map<String,Object>> handleDuplicateURLException(DuplicateURLException ex){
        return ResponseDto.builder().message(ex.getMessage()).statusCode(HttpStatus.CONFLICT).build().getResponse();
    }
    @ExceptionHandler(SnapLinkException.class)
    public ResponseEntity<Map<String,Object>> handleSnapLinkException(SnapLinkException ex){
        return ResponseDto.builder()
                .message(ex.getMessage())
                .success(ex.isSuccess())
                .data(ex.getData())
                .statusCode(ex.getStatusCode()).build().getResponse();
    }
}
