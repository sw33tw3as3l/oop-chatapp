package com.ap.chat.common.model;

import java.io.Serializable;

public class Message implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private String sender;
    private long timestamp;
    private MessageType type;
    private String content;

    // fir File
    private String fileId;
    private String fileName;

    public Message() {}

    public Message(String id, String sender, long timestamp, MessageType type, String content) {
        this.id = id;
        this.sender = sender;
        this.timestamp = timestamp;
        this.type = type;
        this.content = content;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getSender() { return sender; }
    public void setSender(String sender) { this.sender = sender; }

    public long getTimestamp() { return timestamp; }
    public void setTimestamp(long timestamp) { this.timestamp = timestamp; }

    public MessageType getType() { return type; }
    public void setType(MessageType type) { this.type = type; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public String getFileId() { return fileId; }
    public void setFileId(String fileId) { this.fileId = fileId; }

    public String getFileName() { return fileName; }
    public void setFileName(String fileName) { this.fileName = fileName; }

    @Override
    public String toString() {
        return "Message{id='" + id + "', sender='" + sender + "', timestamp=" + timestamp +
                ", type=" + type + ", content='" + content + "', fileId='" + fileId + "', fileName='" + fileName + "'}";
    }
}
