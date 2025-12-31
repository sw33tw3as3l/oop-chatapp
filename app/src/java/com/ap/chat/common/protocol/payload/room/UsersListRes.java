package com.ap.chat.common.protocol.payload.room;

import java.io.Serializable;
import java.util.List;

public class UsersListRes implements Serializable {
    private static final long serialVersionUID = 1L;
    private String room;
    private List<String> users;

    public UsersListRes() {}
    public UsersListRes(String room, List<String> users) {
        this.room = room;
        this.users = users;
    }

    public String getRoom() { return room; }
    public void setRoom(String room) { this.room = room; }

    public List<String> getUsers() { return users; }
    public void setUsers(List<String> users) { this.users = users; }

    @Override
    public String toString() {
        return "UsersListRes{room='" + room + "', users=" + users + "}";
    }
}
