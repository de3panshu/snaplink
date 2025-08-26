package com.deepanshu.snaplink.utility.constant;

import lombok.Getter;

@Getter
public enum UrlStatus {
    ENABLED(1);

    private final int value;

    UrlStatus(int value){
        this.value = value;
    }

}
