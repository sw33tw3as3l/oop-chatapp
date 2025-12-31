package com.ap.chat.server.command.impl;

import com.ap.chat.common.model.FileMetadata;
import com.ap.chat.common.protocol.Packet;
import com.ap.chat.common.protocol.PacketType;
import com.ap.chat.common.protocol.payload.file.FileDownloadReq;
import com.ap.chat.common.protocol.payload.file.FileDownloadRes;
import com.ap.chat.server.command.*;
import com.ap.chat.server.net.ConnectionContext;
import com.ap.chat.server.state.UserSession;

public class DownloadCommand implements Command {
    private final CommandContext ctx;
    public DownloadCommand(CommandContext ctx) { this.ctx = ctx; }

    @Override
    public CommandResult execute(Packet<Object> packet, ConnectionContext connection, UserSession session) {
        FileDownloadReq req = (FileDownloadReq) packet.getPayload();
        String fileId = (req.getFileId()==null) ? "" : req.getFileId().trim();
        if (fileId.isEmpty()) throw new IllegalArgumentException("fileId is empty");

        FileMetadata meta = ctx.files.getMeta(fileId);
        byte[] bytes = ctx.files.readBytes(fileId);

        return CommandResult.of(new Packet<>(PacketType.FILE_DOWNLOAD_RES, new FileDownloadRes(meta, bytes), packet.getRequestId()), session);
    }
}
