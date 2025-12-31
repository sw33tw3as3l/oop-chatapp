package com.ap.chat.client.command.dispatcher;

import com.ap.chat.client.command.ClientCommand;
import com.ap.chat.client.command.impl.*;

import java.util.HashMap;
import java.util.Map;

public class ClientCommandDispatcher {
    private final Map<String, ClientCommand> map = new HashMap<>();

    public ClientCommandDispatcher() {
        map.put("help", new HelpCommand());
        map.put("exit", new ExitCommand());
        map.put("register", new RegisterCommand());
        map.put("login", new LoginCommand());
        map.put("create", new CreateRoomCommand());
        map.put("join", new JoinRoomCommand());
        map.put("leave", new LeaveRoomCommand());
        map.put("rooms", new RoomsCommand());
        map.put("users", new UsersCommand());
        map.put("upload", new UploadCommand());
        map.put("download", new DownloadCommand());
        map.put("export", new ExportCommand());
    }

    public ClientCommand resolve(String name) {
        return map.get(name.toLowerCase());
    }
}