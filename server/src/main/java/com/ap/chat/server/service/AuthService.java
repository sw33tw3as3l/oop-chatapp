package com.ap.chat.server.service;

import com.ap.chat.common.exception.DuplicateUsernameException;
import com.ap.chat.common.util.Validation;
import com.ap.chat.server.state.ServerState;
import com.ap.chat.server.state.UserSession;

import java.io.ObjectOutputStream;

public class AuthService {
    private final ServerState state;

    public AuthService(ServerState state) { this.state = state; }

    public void register(String username) {
        Validation.requireValidUsername(username);
        String u = username.trim();

        if (state.getRegisteredUsers().contains(u)) {
            throw new DuplicateUsernameException("Username already exists: " + u);
        }
        state.getRegisteredUsers().add(u);
    }

    public UserSession login(String username, ObjectOutputStream out) {
        Validation.requireValidUsername(username);
        String u = username.trim();

        if (!state.getRegisteredUsers().contains(u)) {
            throw new IllegalArgumentException("Username is not registered. Use /register first.");
        }
        if (state.getOnlineUsers().containsKey(u)) {
            throw new DuplicateUsernameException("User already logged in: " + u);
        }

        UserSession s = new UserSession(u, out);
        state.getOnlineUsers().put(u, s);
        return s;
    }
}
