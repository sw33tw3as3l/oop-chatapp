package com.ap.chat.common.protocol.payload.chat;

import java.io.Serializable;

public class RoomEventRes implements Serializable {
    private static final long serialVersionUID = 1L;
    private String room;
    private String text;

    public RoomEventRes() {}
    public RoomEventRes(String room, String text) {
        this.room = room;
        this.text = text;
    }

    public String getRoom() { return room; }
    public void setRoom(String room) { this.room = room; }

    public String getText() { return text; }
    public void setText(String text) { this.text = text; }

    @Override
    public String toString() {
        return "RoomEventRes{room='" + room + "', text='" + text + "'}";
    }
}
