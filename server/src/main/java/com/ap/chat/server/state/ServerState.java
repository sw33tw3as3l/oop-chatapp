package com.ap.chat.server.state;

import com.ap.chat.common.model.FileMetadata;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class ServerState {
    private final Set<String> registeredUsers = ConcurrentHashMap.newKeySet();
    private final Map<String, UserSession> onlineUsers = new ConcurrentHashMap<>();
    private final Map<String, RoomState> rooms = new ConcurrentHashMap<>();
    private final Map<String, FileMetadata> files = new ConcurrentHashMap<>();

    public Set<String> getRegisteredUsers() { return registeredUsers; }
    public Map<String, UserSession> getOnlineUsers() { return onlineUsers; }
    public Map<String, RoomState> getRooms() { return rooms; }
    public Map<String, FileMetadata> getFiles() { return files; }

    public void onDisconnect(UserSession session) {
        if (session == null) return;
        String u = session.getUsername();
        String room = session.getCurrentRoom();
        if (room != null) {
            RoomState rs = rooms.get(room);
            if (rs != null) rs.removeMember(u);
        }
        onlineUsers.remove(u);
    }
}
