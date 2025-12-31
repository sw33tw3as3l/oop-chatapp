package com.ap.chat.server.service;

import com.ap.chat.common.exception.RoomNotFoundException;
import com.ap.chat.common.util.Validation;
import com.ap.chat.server.state.RoomState;
import com.ap.chat.server.state.ServerState;
import com.ap.chat.server.state.UserSession;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RoomService {
    public static final String LOBBY = "lobby";
    private final ServerState state;

    public RoomService(ServerState state) { this.state = state; }

    public void ensureLobbyExists() {
        state.getRooms().computeIfAbsent(LOBBY, RoomState::new);
    }

    public void createRoom(String roomName) {
        Validation.requireValidRoomName(roomName);
        String r = roomName.trim();
        state.getRooms().computeIfAbsent(r, RoomState::new);
    }

    public void joinRoom(UserSession session, String roomName) {
        Validation.requireValidRoomName(roomName);
        String r = roomName.trim();

        RoomState room = state.getRooms().get(r);
        if (room == null) throw new RoomNotFoundException("Room not found: " + r);

        leaveIfAny(session);
        room.addMember(session.getUsername());
        session.setCurrentRoom(r);
    }

    public void leaveIfAny(UserSession session) {
        String cur = session.getCurrentRoom();
        if (cur == null) return;

        RoomState room = state.getRooms().get(cur);
        if (room != null) room.removeMember(session.getUsername());
        session.setCurrentRoom(null);
    }

    public List<String> listRooms() {
        List<String> rooms = new ArrayList<>(state.getRooms().keySet());
        Collections.sort(rooms);
        return rooms;
    }

    public List<String> listUsersInCurrentRoom(UserSession session) {
        String cur = session.getCurrentRoom();
        if (cur == null) throw new IllegalStateException("You are not in any room. Use /join <room>.");

        RoomState room = state.getRooms().get(cur);
        if (room == null) throw new RoomNotFoundException("Room not found: " + cur);

        List<String> users = room.snapshotMembers();
        Collections.sort(users);
        return users;
    }
}
