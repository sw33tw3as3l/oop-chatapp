package com.ap.chat.server.command.impl;

import com.ap.chat.common.model.Message;
import com.ap.chat.common.model.MessageType;
import com.ap.chat.common.protocol.Packet;
import com.ap.chat.common.protocol.PacketType;
import com.ap.chat.common.protocol.payload.chat.ChatEventRes;
import com.ap.chat.common.protocol.payload.chat.RoomEventRes;
import com.ap.chat.common.protocol.payload.room.JoinRoomReq;
import com.ap.chat.common.util.IdGenerator;
import com.ap.chat.server.command.*;
import com.ap.chat.server.net.ConnectionContext;
import com.ap.chat.server.state.UserSession;

public class JoinRoomCommand implements Command {
    private final CommandContext ctx;
    public JoinRoomCommand(CommandContext ctx) { this.ctx = ctx; }

    @Override
    public CommandResult execute(Packet<Object> packet, ConnectionContext connection, UserSession session) {
        JoinRoomReq req = (JoinRoomReq) packet.getPayload();
        ctx.rooms.joinRoom(session, req.getRoomName());

        Message sys = new Message(IdGenerator.uuid(), "SYSTEM", System.currentTimeMillis(), MessageType.SYSTEM,
                session.getUsername() + " joined the room.");
        ctx.messages.appendToCurrentRoom(session, sys);
        ctx.broadcast.toRoom(session.getCurrentRoom(), new Packet<>(PacketType.CHAT_EVENT, new ChatEventRes(session.getCurrentRoom(), sys), null));

        return CommandResult.of(new Packet<>(PacketType.ROOM_EVENT, new RoomEventRes(session.getCurrentRoom(), "Joined room: " + session.getCurrentRoom()), packet.getRequestId()), session);
    }
}
