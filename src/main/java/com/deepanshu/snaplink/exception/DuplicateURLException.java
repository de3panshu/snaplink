package com.deepanshu.snaplink.exception;

public class DuplicateURLException extends RuntimeException{
    public DuplicateURLException(String url){
        super(String.format("Url: %s is not available.",url));
    }
}
