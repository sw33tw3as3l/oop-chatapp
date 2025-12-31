package com.ap.chat.server.command.impl;

import com.ap.chat.common.protocol.Packet;
import com.ap.chat.common.protocol.PacketType;
import com.ap.chat.common.protocol.payload.room.RoomsListRes;
import com.ap.chat.server.command.*;
import com.ap.chat.server.net.ConnectionContext;
import com.ap.chat.server.state.UserSession;

public class RoomsCommand implements Command {
    private final CommandContext ctx;
    public RoomsCommand(CommandContext ctx) { this.ctx = ctx; }

    @Override
    public CommandResult execute(Packet<Object> packet, ConnectionContext connection, UserSession session) {
        return CommandResult.of(new Packet<>(PacketType.ROOMS_RES, new RoomsListRes(ctx.rooms.listRooms()), packet.getRequestId()), session);
    }
}
