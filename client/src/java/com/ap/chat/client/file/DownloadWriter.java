package com.ap.chat.client.file;

import com.ap.chat.common.exception.FileTransferException;
import com.ap.chat.common.model.FileMetadata;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class DownloadWriter {

    public static Path resolveTarget(String savePath, FileMetadata meta) {
        if (savePath == null || savePath.trim().isEmpty()) throw new FileTransferException("savePath is empty");
        Path p = Paths.get(savePath);
        if (Files.exists(p) && Files.isDirectory(p)) {
            return p.resolve(meta.getOriginalName());
        }
        return p;
    }

    public static void write(byte[] bytes, Path target) {
        try {
            Path parent = target.getParent();
            if (parent != null && !Files.exists(parent)) Files.createDirectories(parent);
            Files.write(target, bytes);
        } catch (Exception e) {
            throw new FileTransferException("Failed to write file to: " + target, e);
        }
    }
}
