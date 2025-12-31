package com.ap.chat.common.protocol.payload.room;

import java.io.Serializable;
import java.util.List;

public class RoomsListRes implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<String> rooms;

    public RoomsListRes() {}
    public RoomsListRes(List<String> rooms) { this.rooms = rooms; }

    public List<String> getRooms() { return rooms; }
    public void setRooms(List<String> rooms) { this.rooms = rooms; }

    @Override
    public String toString() {
        return "RoomsListRes{rooms=" + rooms + "}";
    }
}
