package com.ap.chat.common.protocol.payload.export;

import com.ap.chat.common.model.Message;

import java.io.Serializable;
import java.util.List;

public class ExportDataRes implements Serializable {
    private static final long serialVersionUID = 1L;

    private String room;
    private long exportedAt; // millis
    private List<Message> messages;

    public ExportDataRes() {}
    public ExportDataRes(String room, long exportedAt, List<Message> messages) {
        this.room = room;
        this.exportedAt = exportedAt;
        this.messages = messages;
    }

    public String getRoom() { return room; }
    public void setRoom(String room) { this.room = room; }

    public long getExportedAt() { return exportedAt; }
    public void setExportedAt(long exportedAt) { this.exportedAt = exportedAt; }

    public List<Message> getMessages() { return messages; }
    public void setMessages(List<Message> messages) { this.messages = messages; }

    @Override
    public String toString() {
        return "ExportDataRes{room='" + room + "', exportedAt=" + exportedAt + ", messages=" + (messages==null?0:messages.size()) + "}";
    }
}
