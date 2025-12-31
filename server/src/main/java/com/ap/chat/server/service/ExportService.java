package com.ap.chat.server.service;

import com.ap.chat.common.protocol.payload.export.ExportDataRes;
import com.ap.chat.server.state.UserSession;

public class ExportService {
    private final MessageService messages;

    public ExportService(MessageService messages) {
        this.messages = messages;
    }

    public ExportDataRes exportLast(UserSession session, int n) {
        return new ExportDataRes(session.getCurrentRoom(), System.currentTimeMillis(), messages.lastN(session, n));
    }
}
