package com.ap.chat.client.command.impl;

import com.ap.chat.client.command.ClientCommand;
import com.ap.chat.client.command.ClientContext;
import com.ap.chat.client.net.TcpClient;
import com.ap.chat.common.protocol.Packet;
import com.ap.chat.common.protocol.PacketType;
import com.ap.chat.common.protocol.payload.room.JoinRoomReq;
import com.ap.chat.common.util.Validation;

public class JoinRoomCommand implements ClientCommand {
    @Override
    public void execute(String[] args, ClientContext ctx, TcpClient client) throws Exception {
        if (!ctx.isLoggedIn()) { System.out.println("Please /login first."); return; }
        if (args.length < 1) { System.out.println("Usage: /join <roomName>"); return; }

        String room = args[0];
        Validation.requireValidRoomName(room);

        String rid = client.nextRequestId();
        client.send(new Packet<>(PacketType.JOIN_ROOM_REQ, new JoinRoomReq(room), rid));
    }
}