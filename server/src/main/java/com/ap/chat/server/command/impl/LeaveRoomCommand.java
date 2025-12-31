package com.ap.chat.server.command.impl;

import com.ap.chat.common.model.Message;
import com.ap.chat.common.model.MessageType;
import com.ap.chat.common.protocol.Packet;
import com.ap.chat.common.protocol.PacketType;
import com.ap.chat.common.protocol.payload.chat.ChatEventRes;
import com.ap.chat.common.protocol.payload.chat.RoomEventRes;
import com.ap.chat.common.util.IdGenerator;
import com.ap.chat.server.command.*;
import com.ap.chat.server.net.ConnectionContext;
import com.ap.chat.server.state.UserSession;

public class LeaveRoomCommand implements Command {
    private final CommandContext ctx;
    public LeaveRoomCommand(CommandContext ctx) { this.ctx = ctx; }

    @Override
    public CommandResult execute(Packet<Object> packet, ConnectionContext connection, UserSession session) {
        String cur = session.getCurrentRoom();
        if (cur == null) throw new IllegalStateException("You are not in any room.");

        Message sys = new Message(IdGenerator.uuid(), "SYSTEM", System.currentTimeMillis(), MessageType.SYSTEM,
                session.getUsername() + " left the room.");
        ctx.messages.appendToCurrentRoom(session, sys);
        ctx.broadcast.toRoom(cur, new Packet<>(PacketType.CHAT_EVENT, new ChatEventRes(cur, sys), null));

        ctx.rooms.leaveIfAny(session);
        return CommandResult.of(new Packet<>(PacketType.ROOM_EVENT, new RoomEventRes(null, "Left room."), packet.getRequestId()), session);
    }
}
