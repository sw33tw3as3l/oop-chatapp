package com.ap.chat.server.app;

import com.ap.chat.server.net.TcpServer;

public class ServerMain {
    public static void main(String[] args) throws Exception {
        int port = 5555;
        if (args.length >= 1) port = Integer.parseInt(args[0]);
        new TcpServer(port).start();
    }
}
