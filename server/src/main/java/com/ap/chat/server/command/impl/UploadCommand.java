package com.ap.chat.server.command.impl;

import com.ap.chat.common.model.FileMetadata;
import com.ap.chat.common.model.Message;
import com.ap.chat.common.model.MessageType;
import com.ap.chat.common.protocol.Packet;
import com.ap.chat.common.protocol.PacketType;
import com.ap.chat.common.protocol.payload.chat.ChatEventRes;
import com.ap.chat.common.protocol.payload.file.FileUploadReq;
import com.ap.chat.common.protocol.payload.file.FileUploadRes;
import com.ap.chat.common.util.IdGenerator;
import com.ap.chat.server.command.*;
import com.ap.chat.server.net.ConnectionContext;
import com.ap.chat.server.state.UserSession;

public class UploadCommand implements Command {
    private final CommandContext ctx;
    public UploadCommand(CommandContext ctx) { this.ctx = ctx; }

    @Override
    public CommandResult execute(Packet<Object> packet, ConnectionContext connection, UserSession session) {
        FileUploadReq req = (FileUploadReq) packet.getPayload();
        FileMetadata meta = ctx.files.saveTxt(session, req.getOriginalName(), req.getBytes());

        Message fm = new Message(IdGenerator.uuid(), session.getUsername(), System.currentTimeMillis(), MessageType.FILE,
                "uploaded file: " + meta.getOriginalName() + " (fileId=" + meta.getFileId() + ")");
        fm.setFileId(meta.getFileId());
        fm.setFileName(meta.getOriginalName());

        ctx.messages.appendToCurrentRoom(session, fm);
        ctx.broadcast.toRoom(session.getCurrentRoom(), new Packet<>(PacketType.CHAT_EVENT, new ChatEventRes(session.getCurrentRoom(), fm), null));

        return CommandResult.of(new Packet<>(PacketType.FILE_UPLOAD_RES, new FileUploadRes(true, "File uploaded. fileId=" + meta.getFileId(), meta), packet.getRequestId()), session);
    }
}
