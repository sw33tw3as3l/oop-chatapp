package com.ap.chat.common.protocol.payload.chat;

import java.io.Serializable;

public class ChatMsgReq implements Serializable {
    private static final long serialVersionUID = 1L;
    private String text;

    public ChatMsgReq() {}
    public ChatMsgReq(String text) { this.text = text; }

    public String getText() { return text; }
    public void setText(String text) { this.text = text; }

    @Override
    public String toString() {
        return "ChatMsgReq{text='" + text + "'}";
    }
}
