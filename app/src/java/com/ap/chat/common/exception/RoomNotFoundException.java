package com.ap.chat.common.exception;

public class RoomNotFoundException extends RuntimeException {
    public RoomNotFoundException(String message) { super(message); }
}
