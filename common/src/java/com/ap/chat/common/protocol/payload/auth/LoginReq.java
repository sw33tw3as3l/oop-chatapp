package com.ap.chat.common.protocol.payload.auth;

import java.io.Serializable;

public class LoginReq implements Serializable {
    private static final long serialVersionUID = 1L;
    private String username;

    public LoginReq() {}
    public LoginReq(String username) { this.username = username; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    @Override public String toString() { return "LoginReq{username='" + username + "'}"; }
}
