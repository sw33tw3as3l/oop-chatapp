package com.ap.chat.server.state;

import com.ap.chat.common.protocol.Packet;

import java.io.IOException;
import java.io.ObjectOutputStream;

public class UserSession {
    private final String username;
    private final ObjectOutputStream out;
    private volatile boolean loggedIn = true;
    private volatile String currentRoom = null;

    public UserSession(String username, ObjectOutputStream out) {
        this.username = username;
        this.out = out;
    }

    public String getUsername() { return username; }
    public boolean isLoggedIn() { return loggedIn; }
    public void setLoggedIn(boolean loggedIn) { this.loggedIn = loggedIn; }

    public String getCurrentRoom() { return currentRoom; }
    public void setCurrentRoom(String currentRoom) { this.currentRoom = currentRoom; }

    // Critical: ObjectOutputStream is not thread-safe
    public synchronized void send(Packet<?> packet) throws IOException {
        out.writeObject(packet);
        out.flush();
    }
}
