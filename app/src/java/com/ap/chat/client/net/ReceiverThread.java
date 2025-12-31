package com.ap.chat.client.net;

import com.ap.chat.common.model.Message;
import com.ap.chat.common.model.MessageType;
import com.ap.chat.common.protocol.Packet;
import com.ap.chat.common.protocol.PacketType;
import com.ap.chat.common.protocol.payload.auth.AuthRes;
import com.ap.chat.common.protocol.payload.chat.ChatEventRes;
import com.ap.chat.common.protocol.payload.chat.RoomEventRes;
import com.ap.chat.common.protocol.payload.error.ErrorPayload;
import com.ap.chat.common.protocol.payload.export.ExportDataRes;
import com.ap.chat.common.protocol.payload.file.FileDownloadRes;
import com.ap.chat.common.protocol.payload.file.FileUploadRes;
import com.ap.chat.common.protocol.payload.room.RoomsListRes;
import com.ap.chat.common.protocol.payload.room.UsersListRes;
import com.ap.chat.common.util.TimeUtil;

import java.io.EOFException;
import java.net.SocketException;

public class ReceiverThread extends Thread {
    private final TcpClient client;

    public ReceiverThread(TcpClient client) {
        super("receiver-thread");
        this.client = client;
        setDaemon(true);
    }

    @Override
    public void run() {
        try {
            while (client.isRunning() && !isInterrupted()) {
                Object obj = client.getCtx().getIn().readObject();
                if (!(obj instanceof Packet)) continue;
                Packet<?> packet = (Packet<?>) obj;

                ResponseHandler h = client.takeHandler(packet.getRequestId());
                if (h != null) {
                    h.handle(packet);
                    continue;
                }

                print(packet);
            }
        } catch (EOFException | SocketException e) {
            System.out.println("Disconnected from server.");
        } catch (Exception e) {
            if (client.isRunning()) System.out.println("Receiver error: " + e.getMessage());
        } finally {
            client.stop();
        }
    }

    private void print(Packet<?> packet) {
        if (packet.getType() == PacketType.ERROR) {
            ErrorPayload ep = (ErrorPayload) packet.getPayload();
            System.out.println("[ERROR] " + ep.getCode() + ": " + ep.getMessage());
            return;
        }

        switch (packet.getType()) {
            case REGISTER_RES: {
                AuthRes r = (AuthRes) packet.getPayload();
                System.out.println("[REGISTER] " + r.getMessage());
                break;
            }
            case LOGIN_RES: {
                AuthRes r = (AuthRes) packet.getPayload();
                System.out.println("[LOGIN] " + r.getMessage());
                break;
            }
            case ROOM_EVENT: {
                RoomEventRes ev = (RoomEventRes) packet.getPayload();
                String room = ev.getRoom() == null ? "-" : ev.getRoom();
                System.out.println("[ROOM][" + room + "] " + ev.getText());
                break;
            }
            case CHAT_EVENT: {
                ChatEventRes ev = (ChatEventRes) packet.getPayload();
                Message m = ev.getMessage();
                String ts = TimeUtil.isoFromMillis(m.getTimestamp());
                if (m.getType() == MessageType.SYSTEM) {
                    System.out.println("[" + ev.getRoom() + "][" + ts + "] " + m.getContent());
                } else {
                    System.out.println("[" + ev.getRoom() + "][" + ts + "] " + m.getSender() + ": " + m.getContent());
                }
                break;
            }
            case ROOMS_RES: {
                RoomsListRes r = (RoomsListRes) packet.getPayload();
                System.out.println("Rooms: " + r.getRooms());
                break;
            }
            case USERS_RES: {
                UsersListRes r = (UsersListRes) packet.getPayload();
                System.out.println("Users in " + r.getRoom() + ": " + r.getUsers());
                break;
            }
            case FILE_UPLOAD_RES: {
                FileUploadRes r = (FileUploadRes) packet.getPayload();
                System.out.println("[UPLOAD] " + r.getMessage());
                break;
            }
            case FILE_DOWNLOAD_RES: {
                FileDownloadRes r = (FileDownloadRes) packet.getPayload();
                System.out.println("[DOWNLOAD] Received " + r.getMetadata().getOriginalName() + " (" + r.getMetadata().getFileId() + ")");
                break;
            }
            case EXPORT_DATA_RES: {
                ExportDataRes r = (ExportDataRes) packet.getPayload();
                System.out.println("[EXPORT] Received export data room=" + r.getRoom() + " messages=" + (r.getMessages()==null?0:r.getMessages().size()));
                break;
            }
            default:
                System.out.println("[RECV] " + packet.getType() + " payload=" + packet.getPayload());
        }
    }
}
