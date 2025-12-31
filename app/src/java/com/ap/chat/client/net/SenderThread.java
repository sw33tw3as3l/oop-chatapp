package com.ap.chat.client.net;

import com.ap.chat.client.cli.HelpPrinter;
import com.ap.chat.client.cli.Parser;
import com.ap.chat.client.command.ClientCommand;
import com.ap.chat.client.command.ClientContext;
import com.ap.chat.client.command.dispatcher.ClientCommandDispatcher;
import com.ap.chat.common.protocol.Packet;
import com.ap.chat.common.protocol.PacketType;
import com.ap.chat.common.protocol.payload.chat.ChatMsgReq;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class SenderThread extends Thread {
    private final TcpClient client;
    private final ClientContext ctx;
    private final ClientCommandDispatcher dispatcher;

    public SenderThread(TcpClient client) {
        super("sender-thread");
        this.client = client;
        this.ctx = new ClientContext();
        this.dispatcher = new ClientCommandDispatcher();
    }

    @Override
    public void run() {
        HelpPrinter.printHelp();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            while (client.isRunning()) {
                System.out.print("> ");
                String line = br.readLine();
                if (line == null) break;
                line = line.trim();
                if (line.isEmpty()) continue;

                if (line.startsWith("/")) {
                    Parser.ParsedCommand pc = Parser.parse(line);
                    ClientCommand cmd = dispatcher.resolve(pc.name);
                    if (cmd == null) {
                        System.out.println("Unknown command. Use /help");
                        continue;
                    }
                    cmd.execute(pc.args, ctx, client);
                } else {
                    if (!ctx.isLoggedIn()) {
                        System.out.println("Please /login first.");
                        continue;
                    }
                    client.send(new Packet<>(PacketType.CHAT_MSG_REQ, new ChatMsgReq(line), null));
                }
            }
        } catch (Exception e) {
            if (client.isRunning()) System.out.println("Sender error: " + e.getMessage());
        } finally {
            client.stop();
        }
    }
}
