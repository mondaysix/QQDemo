package com.example.entity;

import android.graphics.Bitmap;

/**
 * Created by Administrator on 2016/10/17.
 */
public class UserEntity {
    private String userHeader;
    private String nickName;
    private String signature;

    public UserEntity() {
    }

    public UserEntity(String nickName) {
        this.nickName = nickName;
    }

    public String getUserHeader() {
        return userHeader;
    }

    public void setUserHeader(String userHeader) {
        this.userHeader = userHeader;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }
}
