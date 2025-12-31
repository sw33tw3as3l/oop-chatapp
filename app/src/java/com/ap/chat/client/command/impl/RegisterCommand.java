package com.ap.chat.client.command.impl;

import com.ap.chat.client.command.ClientCommand;
import com.ap.chat.client.command.ClientContext;
import com.ap.chat.client.net.TcpClient;
import com.ap.chat.common.protocol.Packet;
import com.ap.chat.common.protocol.PacketType;
import com.ap.chat.common.protocol.payload.auth.RegisterReq;
import com.ap.chat.common.util.Validation;

public class RegisterCommand implements ClientCommand {
    @Override
    public void execute(String[] args, ClientContext ctx, TcpClient client) throws Exception {
        // check the usage of Register
        if (args.length < 1) {
            System.out.println("Usage: /register <username>");
            return;
        }
        String username = args[0];
        Validation.requireValidUsername(username);

        String rid = client.nextRequestId();
        client.send(new Packet<>(PacketType.REGISTER_REQ, new RegisterReq(username), rid));
    }
}