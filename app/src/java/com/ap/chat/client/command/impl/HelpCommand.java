package com.ap.chat.client.command.impl;

import com.ap.chat.client.cli.HelpPrinter;
import com.ap.chat.client.command.ClientCommand;
import com.ap.chat.client.command.ClientContext;
import com.ap.chat.client.net.TcpClient;

public class HelpCommand implements ClientCommand {
    @Override
    public void execute(String[] args, ClientContext ctx, TcpClient client) {
        HelpPrinter.printHelp();
    }
}
