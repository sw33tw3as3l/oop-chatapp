package com.ap.chat.client.command;

import com.ap.chat.client.net.TcpClient;

public interface ClientCommand {
    void execute(String[] args, ClientContext ctx, TcpClient client) throws Exception;
}
