package com.ap.chat.server.command.dispatcher;

import com.ap.chat.common.protocol.Packet;
import com.ap.chat.common.protocol.PacketType;
import com.ap.chat.common.protocol.payload.error.ErrorPayload;
import com.ap.chat.server.command.Command;
import com.ap.chat.server.command.CommandContext;
import com.ap.chat.server.command.CommandResult;
import com.ap.chat.server.error.ExceptionManager;
import com.ap.chat.server.net.ConnectionContext;
import com.ap.chat.server.state.UserSession;
import com.ap.chat.server.service.*;

import com.ap.chat.server.command.impl.*;

import java.util.EnumMap;
import java.util.Map;

public class ServerCommandDispatcher {

    public static class Result {
        public final Packet<?> response;
        public final UserSession session;
        public Result(Packet<?> response, UserSession session) {
            this.response = response;
            this.session = session;
        }
    }

    private final CommandContext ctx;
    private final Map<PacketType, Command> map = new EnumMap<>(PacketType.class);

    public ServerCommandDispatcher(AuthService auth, RoomService rooms, MessageService messages,
                                  FileStorageService files, ExportService export, BroadcastService broadcast) {
        this.ctx = new CommandContext(auth, rooms, messages, files, export, broadcast);

        map.put(PacketType.REGISTER_REQ, new RegisterCommand(ctx));
        map.put(PacketType.LOGIN_REQ, new LoginCommand(ctx));

        map.put(PacketType.CREATE_ROOM_REQ, new CreateRoomCommand(ctx));
        map.put(PacketType.JOIN_ROOM_REQ, new JoinRoomCommand(ctx));
        map.put(PacketType.LEAVE_ROOM_REQ, new LeaveRoomCommand(ctx));
        map.put(PacketType.ROOMS_REQ, new RoomsCommand(ctx));
        map.put(PacketType.USERS_REQ, new UsersCommand(ctx));

        map.put(PacketType.CHAT_MSG_REQ, new ChatMsgCommand(ctx));

        map.put(PacketType.FILE_UPLOAD_REQ, new UploadCommand(ctx));
        map.put(PacketType.FILE_DOWNLOAD_REQ, new DownloadCommand(ctx));

        map.put(PacketType.EXPORT_LAST_REQ, new ExportLastCommand(ctx));
    }

    public void ensureLobby() {
        ctx.rooms.ensureLobbyExists();
    }

    public Result dispatch(Packet<Object> packet, ConnectionContext connection, UserSession session) {
        try {
            Command cmd = map.get(packet.getType());
            if (cmd == null) {
                return new Result(new Packet<>(PacketType.ERROR,
                        new ErrorPayload("Unsupported", "Unsupported packet: " + packet.getType()),
                        packet.getRequestId()), session);
            }

            // auth check: only REGISTER/LOGIN allowed without session
            if (session == null && !(packet.getType() == PacketType.REGISTER_REQ || packet.getType() == PacketType.LOGIN_REQ)) {
                return new Result(new Packet<>(PacketType.ERROR,
                        new ErrorPayload("AuthRequired", "Please /login first."),
                        packet.getRequestId()), session);
            }

            CommandResult r = cmd.execute(packet, connection, session);
            return new Result(r.response, r.session);

        } catch (Exception e) {
            Packet<ErrorPayload> err = ExceptionManager.toErrorPacket(e);
            err.setRequestId(packet.getRequestId());
            return new Result(err, session);
        }
    }
}
