package com.ap.chat.server.service;

import com.ap.chat.common.protocol.Packet;
import com.ap.chat.server.state.RoomState;
import com.ap.chat.server.state.ServerState;
import com.ap.chat.server.state.UserSession;

import java.util.List;

public class BroadcastService {
    private final ServerState state;

    public BroadcastService(ServerState state) { this.state = state; }

    public void toRoom(String room, Packet<?> packet) {
        RoomState rs = state.getRooms().get(room);
        if (rs == null) return;

        List<String> members = rs.snapshotMembers();
        for (String u : members) {
            UserSession s = state.getOnlineUsers().get(u);
            if (s == null) continue;
            try { s.send(packet); } catch (Exception ignored) {}
        }
    }
}
