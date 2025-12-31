package com.ap.chat.server.command;

import com.ap.chat.server.service.*;

public class CommandContext {
    public final AuthService auth;
    public final RoomService rooms;
    public final MessageService messages;
    public final FileStorageService files;
    public final ExportService export;
    public final BroadcastService broadcast;

    public CommandContext(AuthService auth, RoomService rooms, MessageService messages,
                          FileStorageService files, ExportService export, BroadcastService broadcast) {
        this.auth = auth;
        this.rooms = rooms;
        this.messages = messages;
        this.files = files;
        this.export = export;
        this.broadcast = broadcast;
    }
}
