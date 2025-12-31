package com.ap.chat.common.protocol.payload.room;

import java.io.Serializable;

public class JoinRoomReq implements Serializable {
    private static final long serialVersionUID = 1L;
    private String roomName;

    public JoinRoomReq() {}
    public JoinRoomReq(String roomName) { this.roomName = roomName; }

    public String getRoomName() { return roomName; }
    public void setRoomName(String roomName) { this.roomName = roomName; }

    @Override public String toString() { return "JoinRoomReq{roomName='" + roomName + "'}"; }
}
