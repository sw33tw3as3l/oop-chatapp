package com.ap.chat.server.command.impl;

import com.ap.chat.common.protocol.Packet;
import com.ap.chat.common.protocol.PacketType;
import com.ap.chat.common.protocol.payload.export.ExportDataRes;
import com.ap.chat.common.protocol.payload.export.ExportLastReq;
import com.ap.chat.common.util.Validation;
import com.ap.chat.server.command.*;
import com.ap.chat.server.net.ConnectionContext;
import com.ap.chat.server.state.UserSession;

public class ExportLastCommand implements Command {
    private final CommandContext ctx;
    public ExportLastCommand(CommandContext ctx) { this.ctx = ctx; }

    @Override
    public CommandResult execute(Packet<Object> packet, ConnectionContext connection, UserSession session) {
        ExportLastReq req = (ExportLastReq) packet.getPayload();
        Validation.requireValidExportN(req.getN());
        ExportDataRes data = ctx.export.exportLast(session, req.getN());
        return CommandResult.of(new Packet<>(PacketType.EXPORT_DATA_RES, data, packet.getRequestId()), session);
    }
}
