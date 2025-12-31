package com.ap.chat.server.command.impl;

import com.ap.chat.common.protocol.Packet;
import com.ap.chat.common.protocol.PacketType;
import com.ap.chat.common.protocol.payload.auth.AuthRes;
import com.ap.chat.common.protocol.payload.auth.RegisterReq;
import com.ap.chat.server.command.*;
import com.ap.chat.server.net.ConnectionContext;
import com.ap.chat.server.state.UserSession;

public class RegisterCommand implements Command {
    private final CommandContext ctx;
    public RegisterCommand(CommandContext ctx) { this.ctx = ctx; }

    @Override
    public CommandResult execute(Packet<Object> packet, ConnectionContext connection, UserSession session) {
        RegisterReq req = (RegisterReq) packet.getPayload();
        ctx.auth.register(req.getUsername());
        return CommandResult.of(new Packet<>(PacketType.REGISTER_RES, new AuthRes(true, "Registered successfully."), packet.getRequestId()), session);
    }
}
