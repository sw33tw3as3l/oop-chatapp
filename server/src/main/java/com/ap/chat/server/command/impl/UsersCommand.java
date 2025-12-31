package com.ap.chat.server.command.impl;

import com.ap.chat.common.protocol.Packet;
import com.ap.chat.common.protocol.PacketType;
import com.ap.chat.common.protocol.payload.room.UsersListRes;
import com.ap.chat.server.command.*;
import com.ap.chat.server.net.ConnectionContext;
import com.ap.chat.server.state.UserSession;

public class UsersCommand implements Command {
    private final CommandContext ctx;
    public UsersCommand(CommandContext ctx) { this.ctx = ctx; }

    @Override
    public CommandResult execute(Packet<Object> packet, ConnectionContext connection, UserSession session) {
        return CommandResult.of(new Packet<>(PacketType.USERS_RES, new UsersListRes(session.getCurrentRoom(), ctx.rooms.listUsersInCurrentRoom(session)), packet.getRequestId()), session);
    }
}
