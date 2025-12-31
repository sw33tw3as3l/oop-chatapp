package com.ap.chat.common.util;

public class Validation {

    public static void requireValidUsername(String username) {
        if (username == null) throw new IllegalArgumentException("username is null");

        String u = username.trim();
        
        if (u.isEmpty()) throw new IllegalArgumentException("username is empty");
        if (u.length() > 20) throw new IllegalArgumentException("username too long (max 20)");
        if (!u.matches("[a-zA-Z0-9_]+")) throw new IllegalArgumentException("username must match [a-zA-Z0-9_]+");
    }

    public static void requireValidRoomName(String roomName) {
        if (roomName == null) throw new IllegalArgumentException("roomName is null");

        String r = roomName.trim();
        
        if (r.isEmpty()) throw new IllegalArgumentException("roomName is empty");
        if (r.length() > 30) throw new IllegalArgumentException("roomName too long (max 30)");
        if (!r.matches("[a-zA-Z0-9_-]+")) throw new IllegalArgumentException("roomName must match [a-zA-Z0-9_-]+");
    }

    public static void requireValidExportN(int n) {
        if (n < 1 || n > 200) throw new IllegalArgumentException("N must be between 1 and 200");
    }
}
