package com.deepanshu.snaplink.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

@Data
@Builder
public class ResponseDto {
    private HttpStatus statusCode;
    private String message;
    private boolean success;
    private Object data;

    public ResponseEntity<Map<String,Object>> getResponse(){
        HashMap<String,Object> responseInfo = new HashMap<>();
        responseInfo.put("message",this.message);
        responseInfo.put("data",this.data);
        responseInfo.put("success",this.success);
        return ResponseEntity.status(this.statusCode).body(responseInfo);
    }
}
