package com.ap.chat.common.protocol.payload.file;

import java.io.Serializable;

public class FileUploadReq implements Serializable {
    private static final long serialVersionUID = 1L;

    private String originalName;
    private byte[] bytes;

    public FileUploadReq() {}
    public FileUploadReq(String originalName, byte[] bytes) {
        this.originalName = originalName;
        this.bytes = bytes;
    }

    public String getOriginalName() { return originalName; }
    public void setOriginalName(String originalName) { this.originalName = originalName; }

    public byte[] getBytes() { return bytes; }
    public void setBytes(byte[] bytes) { this.bytes = bytes; }

    @Override
    public String toString() {
        return "FileUploadReq{originalName='" + originalName + "', size=" + (bytes==null?0:bytes.length) + "}";
    }
}
