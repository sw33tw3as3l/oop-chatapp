package com.ap.chat.server.service;

import com.ap.chat.common.model.Message;
import com.ap.chat.server.state.RoomState;
import com.ap.chat.server.state.ServerState;
import com.ap.chat.server.state.UserSession;

import java.util.List;

public class MessageService {
    private final ServerState state;

    public MessageService(ServerState state) { this.state = state; }

    public void appendToCurrentRoom(UserSession session, Message m) {
        String room = session.getCurrentRoom();
        if (room == null) throw new IllegalStateException("You are not in any room. Use /join <room>.");
        RoomState rs = state.getRooms().get(room);
        if (rs == null) throw new IllegalStateException("Room missing: " + room);
        rs.appendMessage(m);
    }

    public List<Message> lastN(UserSession session, int n) {
        String room = session.getCurrentRoom();
        if (room == null) throw new IllegalStateException("You are not in any room. Use /join <room>.");
        RoomState rs = state.getRooms().get(room);
        if (rs == null) throw new IllegalStateException("Room missing: " + room);
        return rs.lastMessages(n);
    }
}
