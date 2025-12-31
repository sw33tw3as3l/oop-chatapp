package com.ap.chat.server.error;

import com.ap.chat.common.protocol.Packet;
import com.ap.chat.common.protocol.PacketType;
import com.ap.chat.common.protocol.payload.error.ErrorPayload;

public class ExceptionManager {
    public static Packet<ErrorPayload> toErrorPacket(Exception e) {
        String code = e.getClass().getSimpleName();
        String msg = e.getMessage();
        if (msg == null || msg.trim().isEmpty()) msg = "Unexpected error";
        return new Packet<>(PacketType.ERROR, new ErrorPayload(code, msg), null);
    }
}
