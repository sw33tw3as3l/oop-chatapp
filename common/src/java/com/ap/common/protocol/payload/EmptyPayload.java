package com.ap.chat.common.protocol.payload;

import java.io.Serializable;

public class EmptyPayload implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final EmptyPayload INSTANCE = new EmptyPayload();
    private EmptyPayload() {}
    public static EmptyPayload getInstance() { return INSTANCE; }
    @Override public String toString() { return "{}"; }
}
