package com.ap.chat.server.command;

import com.ap.chat.common.protocol.Packet;
import com.ap.chat.server.state.UserSession;

public class CommandResult {
    public final Packet<?> response;
    public final UserSession session;

    public CommandResult(Packet<?> response, UserSession session) {
        this.response = response;
        this.session = session;
    }

    public static CommandResult of(Packet<?> response, UserSession session) {
        return new CommandResult(response, session);
    }
}
