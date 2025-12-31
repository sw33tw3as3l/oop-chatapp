package com.ap.chat.client.export;

import com.ap.chat.common.exception.FileTransferException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ExportWriter {

    public static Path resolveTarget(String savePath, String room) {
        if (savePath == null || savePath.trim().isEmpty()) throw new FileTransferException("savePath is empty");
        Path p = Paths.get(savePath);

        if (Files.exists(p) && Files.isDirectory(p)) {
            return p.resolve(room + "_export.json");
        }
        if (savePath.toLowerCase().endsWith(".json")) return p;
        return Paths.get(savePath + ".json");
    }

    public static void write(Path target, String json) {
        try {
            Path parent = target.getParent();
            if (parent != null && !Files.exists(parent)) Files.createDirectories(parent);
            Files.writeString(target, json);
        } catch (Exception e) {
            throw new FileTransferException("Failed to write JSON to: " + target, e);
        }
    }
}
