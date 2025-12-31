package com.ap.chat.server.command.impl;

import com.ap.chat.common.model.Message;
import com.ap.chat.common.model.MessageType;
import com.ap.chat.common.protocol.Packet;
import com.ap.chat.common.protocol.PacketType;
import com.ap.chat.common.protocol.payload.chat.ChatEventRes;
import com.ap.chat.common.protocol.payload.chat.ChatMsgReq;
import com.ap.chat.common.util.IdGenerator;
import com.ap.chat.server.command.*;
import com.ap.chat.server.net.ConnectionContext;
import com.ap.chat.server.state.UserSession;

public class ChatMsgCommand implements Command {
    private final CommandContext ctx;
    public ChatMsgCommand(CommandContext ctx) { this.ctx = ctx; }

    @Override
    public CommandResult execute(Packet<Object> packet, ConnectionContext connection, UserSession session) {
        ChatMsgReq req = (ChatMsgReq) packet.getPayload();
        String text = (req.getText() == null) ? "" : req.getText().trim();
        if (text.isEmpty()) throw new IllegalArgumentException("Empty message");

        Message m = new Message(IdGenerator.uuid(), session.getUsername(), System.currentTimeMillis(), MessageType.TEXT, text);
        ctx.messages.appendToCurrentRoom(session, m);

        ctx.broadcast.toRoom(session.getCurrentRoom(), new Packet<>(PacketType.CHAT_EVENT, new ChatEventRes(session.getCurrentRoom(), m), null));
        return CommandResult.of(null, session); // broadcast already includes sender
    }
}
