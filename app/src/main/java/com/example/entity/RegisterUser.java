package com.example.entity;

/**
 * Created by Administrator on 2016/10/17.
 */
public class RegisterUser {
    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public RegisterUser(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
