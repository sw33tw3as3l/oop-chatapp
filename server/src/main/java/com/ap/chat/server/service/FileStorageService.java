package com.ap.chat.server.service;

import com.ap.chat.common.exception.FileTransferException;
import com.ap.chat.common.model.FileMetadata;
import com.ap.chat.common.util.IdGenerator;
import com.ap.chat.server.state.ServerState;
import com.ap.chat.server.state.UserSession;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileStorageService {
    public static final long MAX_SIZE_BYTES = 200 * 1024;

    private final ServerState state;

    public FileStorageService(ServerState state) {
        this.state = state;
    }

    public FileMetadata saveTxt(UserSession session, String originalName, byte[] bytes) {
        if (session.getCurrentRoom() == null) throw new IllegalStateException("Join a room before uploading.");
        if (originalName == null || originalName.trim().isEmpty()) throw new FileTransferException("Invalid filename");
        if (!originalName.toLowerCase().endsWith(".txt")) throw new FileTransferException("Only .txt allowed");
        if (bytes == null || bytes.length == 0) throw new FileTransferException("File is empty");
        if (bytes.length > MAX_SIZE_BYTES) throw new FileTransferException("File too large (max 200KB)");

        ensureDir();

        String fileId = IdGenerator.uuid();
        String safeName = originalName.replaceAll("[^a-zA-Z0-9._-]", "_");
        Path path = Paths.get("server_storage", fileId + "_" + safeName);

        try {
            Files.write(path, bytes);
        } catch (Exception e) {
            throw new FileTransferException("Failed to save file on server", e);
        }

        FileMetadata meta = new FileMetadata(fileId, originalName, session.getUsername(), System.currentTimeMillis(), bytes.length, path.toString());
        state.getFiles().put(fileId, meta);
        return meta;
    }

    public FileMetadata getMeta(String fileId) {
        FileMetadata meta = state.getFiles().get(fileId);
        if (meta == null) throw new FileTransferException("Invalid fileId: " + fileId);
        return meta;
    }

    public byte[] readBytes(String fileId) {
        FileMetadata meta = getMeta(fileId);
        try {
            return Files.readAllBytes(Paths.get(meta.getServerPath()));
        } catch (Exception e) {
            throw new FileTransferException("Failed to read file from server storage", e);
        }
    }

    private void ensureDir() {
        Path p = Paths.get("server_storage");
        if (Files.exists(p)) return;
        try {
            Files.createDirectories(p);
        } catch (Exception e) {
            throw new FileTransferException("Failed to create server_storage directory", e);
        }
    }
}
