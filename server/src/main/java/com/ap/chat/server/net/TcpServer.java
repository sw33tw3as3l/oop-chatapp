package com.ap.chat.server.net;

import com.ap.chat.server.command.dispatcher.ServerCommandDispatcher;
import com.ap.chat.server.service.*;
import com.ap.chat.server.state.ServerState;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TcpServer {
    private final int port;
    private final ExecutorService pool;

    private final ServerState state;
    private final ServerCommandDispatcher dispatcher;

    public TcpServer(int port) {
        this.port = port;
        this.pool = Executors.newFixedThreadPool(Math.max(4, Runtime.getRuntime().availableProcessors() * 2));

        this.state = new ServerState();

        AuthService auth = new AuthService(state);
        RoomService rooms = new RoomService(state);
        MessageService messages = new MessageService(state);
        FileStorageService files = new FileStorageService(state);
        ExportService export = new ExportService(messages);
        BroadcastService broadcast = new BroadcastService(state);

        this.dispatcher = new ServerCommandDispatcher(auth, rooms, messages, files, export, broadcast);
        this.dispatcher.ensureLobby(); // lobby at startup
    }

    public void start() throws Exception {
        try (ServerSocket ss = new ServerSocket(port)) {
            System.out.println("Server started on port " + port);
            while (true) {
                Socket s = ss.accept();
                s.setTcpNoDelay(true);
                pool.submit(new ClientHandler(s, dispatcher, state));
            }
        }
    }
}
