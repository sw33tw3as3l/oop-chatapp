package com.ap.chat.common.protocol.payload.file;

import com.ap.chat.common.model.FileMetadata;

import java.io.Serializable;

public class FileUploadRes implements Serializable {
    private static final long serialVersionUID = 1L;

    private boolean success;
    private String message;
    private FileMetadata metadata;

    public FileUploadRes() {}

    public FileUploadRes(boolean success, String message, FileMetadata metadata) {
        this.success = success;
        this.message = message;
        this.metadata = metadata;
    }

    public boolean isSuccess() { return success; }
    public void setSuccess(boolean success) { this.success = success; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public FileMetadata getMetadata() { return metadata; }
    public void setMetadata(FileMetadata metadata) { this.metadata = metadata; }

    @Override
    public String toString() {
        return "FileUploadRes{success=" + success + ", message='" + message + "', metadata=" + metadata + "}";
    }
}
