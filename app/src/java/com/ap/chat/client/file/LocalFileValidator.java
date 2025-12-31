package com.ap.chat.client.file;

import com.ap.chat.common.exception.FileTransferException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LocalFileValidator {
    public static final long MAX_SIZE_BYTES = 200 * 1024;

    public static byte[] readTxtFile(String localPath) {
        if (localPath == null || localPath.trim().isEmpty()) throw new FileTransferException("localPath is empty");
        Path p = Paths.get(localPath);
        if (!Files.exists(p)) throw new FileTransferException("File not found: " + localPath);
        if (!Files.isRegularFile(p)) throw new FileTransferException("Not a file: " + localPath);
        String name = p.getFileName().toString();
        if (!name.toLowerCase().endsWith(".txt")) throw new FileTransferException("Only .txt files allowed");
        try {
            long size = Files.size(p);
            if (size > MAX_SIZE_BYTES) throw new FileTransferException("File too large (max 200KB)");
            return Files.readAllBytes(p);
        } catch (Exception e) {
            throw new FileTransferException("Failed to read local file", e);
        }
    }
}
