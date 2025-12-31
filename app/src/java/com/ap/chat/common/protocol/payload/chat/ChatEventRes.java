package com.ap.chat.common.protocol.payload.chat;

import com.ap.chat.common.model.Message;

import java.io.Serializable;

public class ChatEventRes implements Serializable {
    private static final long serialVersionUID = 1L;
    private String room;
    private Message message;

    public ChatEventRes() {}
    public ChatEventRes(String room, Message message) {
        this.room = room;
        this.message = message;
    }

    public String getRoom() { return room; }
    public void setRoom(String room) { this.room = room; }

    public Message getMessage() { return message; }
    public void setMessage(Message message) { this.message = message; }

    @Override
    public String toString() {
        return "ChatEventRes{room='" + room + "', message=" + message + "}";
    }
}
