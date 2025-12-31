package com.ap.chat.common.model;

import java.io.Serializable;

public class FileMetadata implements Serializable {
    private static final long serialVersionUID = 1L;

    private String fileId;
    private String originalName;
    private String uploader;
    private long uploadTime;
    private long size;
    private String serverPath;

    public FileMetadata() {}

    public FileMetadata(String fileId, String originalName, String uploader, long uploadTime, long size, String serverPath) {
        this.fileId = fileId;
        this.originalName = originalName;
        this.uploader = uploader;
        this.uploadTime = uploadTime;
        this.size = size;
        this.serverPath = serverPath;
    }

    public String getFileId() { return fileId; }
    public void setFileId(String fileId) { this.fileId = fileId; }

    public String getOriginalName() { return originalName; }
    public void setOriginalName(String originalName) { this.originalName = originalName; }

    public String getUploader() { return uploader; }
    public void setUploader(String uploader) { this.uploader = uploader; }

    public long getUploadTime() { return uploadTime; }
    public void setUploadTime(long uploadTime) { this.uploadTime = uploadTime; }

    public long getSize() { return size; }
    public void setSize(long size) { this.size = size; }

    public String getServerPath() { return serverPath; }
    public void setServerPath(String serverPath) { this.serverPath = serverPath; }

    @Override
    public String toString() {
        return "FileMetadata{fileId='" + fileId + "', originalName='" + originalName + "', uploader='" + uploader +
                "', uploadTime=" + uploadTime + ", size=" + size + "}";
    }
}