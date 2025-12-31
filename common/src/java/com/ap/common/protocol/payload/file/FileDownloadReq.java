package com.ap.chat.common.protocol.payload.file;

import java.io.Serializable;

public class FileDownloadReq implements Serializable {
    private static final long serialVersionUID = 1L;
    private String fileId;

    public FileDownloadReq() {}
    public FileDownloadReq(String fileId) { this.fileId = fileId; }

    public String getFileId() { return fileId; }
    public void setFileId(String fileId) { this.fileId = fileId; }

    @Override
    public String toString() {
        return "FileDownloadReq{fileId='" + fileId + "'}";
    }
}
