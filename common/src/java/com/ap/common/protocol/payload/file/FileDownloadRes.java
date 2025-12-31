package com.ap.chat.common.protocol.payload.file;

import com.ap.chat.common.model.FileMetadata;

import java.io.Serializable;

public class FileDownloadRes implements Serializable {
    private static final long serialVersionUID = 1L;

    private FileMetadata metadata;
    private byte[] bytes;

    public FileDownloadRes() {}
    public FileDownloadRes(FileMetadata metadata, byte[] bytes) {
        this.metadata = metadata;
        this.bytes = bytes;
    }

    public FileMetadata getMetadata() { return metadata; }
    public void setMetadata(FileMetadata metadata) { this.metadata = metadata; }

    public byte[] getBytes() { return bytes; }
    public void setBytes(byte[] bytes) { this.bytes = bytes; }

    @Override
    public String toString() {
        return "FileDownloadRes{metadata=" + metadata + ", size=" + (bytes==null?0:bytes.length) + "}";
    }
}
