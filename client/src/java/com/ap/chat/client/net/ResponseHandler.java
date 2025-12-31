package com.ap.chat.client.net;

import com.ap.chat.common.protocol.Packet;

public interface ResponseHandler {
    void handle(Packet<?> packet);
}
