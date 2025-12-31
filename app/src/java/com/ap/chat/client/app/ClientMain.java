package com.ap.chat.client.app;

import com.ap.chat.client.net.*;

public class ClientMain {
    public static void main(String[] args) throws Exception {
        String host = "127.0.0.1";
        int port = 5555;
        if (args.length >= 1) host = args[0];
        if (args.length >= 2) port = Integer.parseInt(args[1]);

        TcpClient client = new TcpClient(host, port);
        client.connect();

        ReceiverThread recv = new ReceiverThread(client);
        SenderThread send = new SenderThread(client);

        recv.start();
        send.start();

        send.join();
        client.close();
        recv.interrupt();
        recv.join(500);
    }
}
