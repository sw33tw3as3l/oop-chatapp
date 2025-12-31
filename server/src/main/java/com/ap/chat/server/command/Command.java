package com.ap.chat.server.command;

import com.ap.chat.common.protocol.Packet;
import com.ap.chat.server.net.ConnectionContext;
import com.ap.chat.server.state.UserSession;

public interface Command {
    CommandResult execute(Packet<Object> packet, ConnectionContext ctx, UserSession session) throws Exception;
}
