package com.ap.chat.client.command.impl;

import com.ap.chat.client.command.ClientCommand;
import com.ap.chat.client.command.ClientContext;
import com.ap.chat.client.file.LocalFileValidator;
import com.ap.chat.client.net.TcpClient;
import com.ap.chat.common.protocol.Packet;
import com.ap.chat.common.protocol.PacketType;
import com.ap.chat.common.protocol.payload.file.FileUploadReq;

import java.nio.file.Paths;

public class UploadCommand implements ClientCommand {
    @Override
    public void execute(String[] args, ClientContext ctx, TcpClient client) throws Exception {
        if (!ctx.isLoggedIn()) { System.out.println("Please /login first."); return; }
        if (args.length < 1) { System.out.println("Usage: /upload <localPath>"); return; }

        String localPath = args[0];
        byte[] bytes = LocalFileValidator.readTxtFile(localPath);
        String originalName = Paths.get(localPath).getFileName().toString();

        String rid = client.nextRequestId();
        client.send(new Packet<>(PacketType.FILE_UPLOAD_REQ, new FileUploadReq(originalName, bytes), rid));
    }
}