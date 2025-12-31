package com.ap.chat.common.protocol.payload.auth;

import java.io.Serializable;

public class RegisterReq implements Serializable {
    private static final long serialVersionUID = 1L;
    private String username;

    public RegisterReq() {}
    public RegisterReq(String username) { this.username = username; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    @Override public String toString() { return "RegisterReq{username='" + username + "'}"; }
}
