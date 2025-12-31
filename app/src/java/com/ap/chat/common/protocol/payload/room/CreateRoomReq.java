package com.ap.chat.common.protocol.payload.room;

import java.io.Serializable;

public class CreateRoomReq implements Serializable {
    private static final long serialVersionUID = 1L;
    private String roomName;

    public CreateRoomReq() {}
    public CreateRoomReq(String roomName) { this.roomName = roomName; }

    public String getRoomName() { return roomName; }
    public void setRoomName(String roomName) { this.roomName = roomName; }

    @Override public String toString() { return "CreateRoomReq{roomName='" + roomName + "'}"; }
}
