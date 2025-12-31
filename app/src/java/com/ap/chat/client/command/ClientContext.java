package com.ap.chat.client.command;

public class ClientContext {
    private volatile boolean loggedIn = false;
    private volatile String username = null;

    public boolean isLoggedIn() { return loggedIn; }
    
    public void setLoggedIn(boolean loggedIn) { this.loggedIn = loggedIn; }

    public String getUsername() { return username; }

    public void setUsername(String username) { this.username = username; }
}
