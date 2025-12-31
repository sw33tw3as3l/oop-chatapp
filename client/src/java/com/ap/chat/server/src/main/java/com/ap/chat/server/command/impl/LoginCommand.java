package com.ap.chat.server.command.impl;

import com.ap.chat.common.model.Message;
import com.ap.chat.common.model.MessageType;
import com.ap.chat.common.protocol.Packet;
import com.ap.chat.common.protocol.PacketType;
import com.ap.chat.common.protocol.payload.auth.AuthRes;
import com.ap.chat.common.protocol.payload.auth.LoginReq;
import com.ap.chat.common.protocol.payload.chat.ChatEventRes;
import com.ap.chat.common.protocol.payload.chat.RoomEventRes;
import com.ap.chat.common.util.IdGenerator;
import com.ap.chat.server.command.*;
import com.ap.chat.server.net.ConnectionContext;
import com.ap.chat.server.state.UserSession;
import com.ap.chat.server.service.RoomService;

public class LoginCommand implements Command {
    private final CommandContext ctx;
    public LoginCommand(CommandContext ctx) { this.ctx = ctx; }

    @Override
    public CommandResult execute(Packet<Object> packet, ConnectionContext connection, UserSession session) throws Exception {
        LoginReq req = (LoginReq) packet.getPayload();

        UserSession newSession = ctx.auth.login(req.getUsername(), connection.getOut());

        // auto join lobby
        ctx.rooms.ensureLobbyExists();
        ctx.rooms.joinRoom(newSession, RoomService.LOBBY);

        // notify user
        newSession.send(new Packet<>(PacketType.ROOM_EVENT, new RoomEventRes(RoomService.LOBBY, "You joined lobby."), null));

        // broadcast system message to lobby + store
        Message sys = new Message(IdGenerator.uuid(), "SYSTEM", System.currentTimeMillis(), MessageType.SYSTEM,
                newSession.getUsername() + " joined the room.");
        ctx.messages.appendToCurrentRoom(newSession, sys);
        ctx.broadcast.toRoom(RoomService.LOBBY, new Packet<>(PacketType.CHAT_EVENT, new ChatEventRes(RoomService.LOBBY, sys), null));

        Packet<AuthRes> res = new Packet<>(PacketType.LOGIN_RES, new AuthRes(true, "Login successful. Joined lobby."), packet.getRequestId());
        return CommandResult.of(res, newSession);
    }
}
