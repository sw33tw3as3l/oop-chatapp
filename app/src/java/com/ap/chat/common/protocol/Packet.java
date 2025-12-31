package com.ap.chat.common.protocol;

import java.io.Serializable;

public class Packet<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    private PacketType type;
    private T payload;
    private long timestamp;
    private String requestId;

    public Packet() {}

    public Packet(PacketType type, T payload) {
        this(type, payload, null);
    }

    public Packet(PacketType type, T payload, String requestId) {
        this.type = type;
        this.payload = payload;
        this.timestamp = System.currentTimeMillis();
        this.requestId = requestId;
    }

    public PacketType getType() { return type; }
    public void setType(PacketType type) { this.type = type; }

    public T getPayload() { return payload; }
    public void setPayload(T payload) { this.payload = payload; }

    public long getTimestamp() { return timestamp; }
    public void setTimestamp(long timestamp) { this.timestamp = timestamp; }

    public String getRequestId() { return requestId; }
    public void setRequestId(String requestId) { this.requestId = requestId; }

    @Override
    public String toString() {
        return "Packet{type=" + type + ", requestId=" + requestId + ", timestamp=" + timestamp + ", payload=" + payload + "}";
    }
}