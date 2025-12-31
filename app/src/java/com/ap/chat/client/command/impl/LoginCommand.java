package com.ap.chat.client.command.impl;

import com.ap.chat.client.command.ClientCommand;
import com.ap.chat.client.command.ClientContext;
import com.ap.chat.client.net.TcpClient;
import com.ap.chat.common.protocol.Packet;
import com.ap.chat.common.protocol.PacketType;
import com.ap.chat.common.protocol.payload.auth.LoginReq;
import com.ap.chat.common.util.Validation;

public class LoginCommand implements ClientCommand {
    @Override
    public void execute(String[] args, ClientContext ctx, TcpClient client) throws Exception {
        // check the usage of login
        if (args.length < 1) {
            System.out.println("Usage: /login <username>");
            return;
        }

        String username = args[0];
        Validation.requireValidUsername(username);

        String rid = client.nextRequestId();
        client.send(new Packet<>(PacketType.LOGIN_REQ, new LoginReq(username), rid));

        ctx.setLoggedIn(true);
        ctx.setUsername(username);
    }
}