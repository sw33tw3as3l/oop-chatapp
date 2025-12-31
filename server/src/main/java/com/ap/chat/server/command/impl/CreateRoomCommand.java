package com.ap.chat.server.command.impl;

import com.ap.chat.common.protocol.Packet;
import com.ap.chat.common.protocol.PacketType;
import com.ap.chat.common.protocol.payload.chat.RoomEventRes;
import com.ap.chat.common.protocol.payload.room.CreateRoomReq;
import com.ap.chat.server.command.*;
import com.ap.chat.server.net.ConnectionContext;
import com.ap.chat.server.state.UserSession;

public class CreateRoomCommand implements Command {
    private final CommandContext ctx;
    public CreateRoomCommand(CommandContext ctx) { this.ctx = ctx; }

    @Override
    public CommandResult execute(Packet<Object> packet, ConnectionContext connection, UserSession session) {
        CreateRoomReq req = (CreateRoomReq) packet.getPayload();
        ctx.rooms.createRoom(req.getRoomName());
        return CommandResult.of(new Packet<>(PacketType.ROOM_EVENT, new RoomEventRes(null, "Room created (if not existed): " + req.getRoomName()), packet.getRequestId()), session);
    }
}
