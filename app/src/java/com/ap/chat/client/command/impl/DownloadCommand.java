package com.ap.chat.client.command.impl;

import com.ap.chat.client.command.ClientCommand;
import com.ap.chat.client.command.ClientContext;
import com.ap.chat.client.file.DownloadWriter;
import com.ap.chat.client.net.ResponseHandler;
import com.ap.chat.client.net.TcpClient;
import com.ap.chat.common.protocol.Packet;
import com.ap.chat.common.protocol.PacketType;
import com.ap.chat.common.protocol.payload.error.ErrorPayload;
import com.ap.chat.common.protocol.payload.file.FileDownloadReq;
import com.ap.chat.common.protocol.payload.file.FileDownloadRes;

import java.nio.file.Path;

public class DownloadCommand implements ClientCommand {
    @Override
    public void execute(String[] args, ClientContext ctx, TcpClient client) throws Exception {
        if (!ctx.isLoggedIn()) { System.out.println("Please /login first."); return; }
        if (args.length < 2) { System.out.println("Usage: /download <fileId> <savePath>"); return; }

        String fileId = args[0];
        String savePath = args[1];

        String rid = client.nextRequestId();
        client.registerHandler(rid, new ResponseHandler() {
            @Override
            public void handle(Packet<?> packet) {
                if (packet.getType() == PacketType.ERROR) {
                    ErrorPayload ep = (ErrorPayload) packet.getPayload();
                    System.out.println("[DOWNLOAD][ERROR] " + ep.getMessage());
                    return;
                }
                if (packet.getType() != PacketType.FILE_DOWNLOAD_RES) {
                    System.out.println("[DOWNLOAD] Unexpected response: " + packet.getType());
                    return;
                }
                FileDownloadRes res = (FileDownloadRes) packet.getPayload();
                Path target = DownloadWriter.resolveTarget(savePath, res.getMetadata());
                DownloadWriter.write(res.getBytes(), target);
                System.out.println("[DOWNLOAD] Saved to: " + target.toAbsolutePath());
            }
        });

        client.send(new Packet<>(PacketType.FILE_DOWNLOAD_REQ, new FileDownloadReq(fileId), rid));
    }
}
