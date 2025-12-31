package com.ap.chat.client.command.impl;

import com.ap.chat.client.command.ClientCommand;
import com.ap.chat.client.command.ClientContext;
import com.ap.chat.client.export.ExportWriter;
import com.ap.chat.client.export.JsonExportBuilder;
import com.ap.chat.client.net.ResponseHandler;
import com.ap.chat.client.net.TcpClient;
import com.ap.chat.common.protocol.Packet;
import com.ap.chat.common.protocol.PacketType;
import com.ap.chat.common.protocol.payload.error.ErrorPayload;
import com.ap.chat.common.protocol.payload.export.ExportDataRes;
import com.ap.chat.common.protocol.payload.export.ExportLastReq;
import com.ap.chat.common.util.Validation;

import java.nio.file.Path;

public class ExportCommand implements ClientCommand {
    @Override
    public void execute(String[] args, ClientContext ctx, TcpClient client) throws Exception {
        if (!ctx.isLoggedIn()) { System.out.println("Please /login first."); return; }
        if (args.length < 3 || !"last".equalsIgnoreCase(args[0])) {
            System.out.println("Usage: /export last <N> <savePath>");
            return;
        }

        int n;
        try {
            n = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            System.out.println("N must be a number.");
            return;
        }

        try {
            Validation.requireValidExportN(n);
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid N: " + e.getMessage());
            return;
        }

        String savePath = args[2];

        String rid = client.nextRequestId();
        client.registerHandler(rid, new ResponseHandler() {
            @Override
            public void handle(Packet<?> packet) {
                if (packet.getType() == PacketType.ERROR) {
                    ErrorPayload ep = (ErrorPayload) packet.getPayload();
                    System.out.println("[EXPORT][ERROR] " + ep.getMessage());
                    return;
                }
                if (packet.getType() != PacketType.EXPORT_DATA_RES) {
                    System.out.println("[EXPORT] Unexpected response: " + packet.getType());
                    return;
                }
                ExportDataRes res = (ExportDataRes) packet.getPayload();
                String json = JsonExportBuilder.build(res.getRoom(), res.getExportedAt(), res.getMessages());
                Path target = ExportWriter.resolveTarget(savePath, res.getRoom());
                ExportWriter.write(target, json);
                System.out.println("[EXPORT] JSON saved to: " + target.toAbsolutePath());
            }
        });

        client.send(new Packet<>(PacketType.EXPORT_LAST_REQ, new ExportLastReq(n), rid));
    }
}