package com.ap.chat.server.net;

import com.ap.chat.common.protocol.Packet;
import com.ap.chat.common.protocol.payload.error.ErrorPayload;
import com.ap.chat.server.command.dispatcher.ServerCommandDispatcher;
import com.ap.chat.server.error.ExceptionManager;
import com.ap.chat.server.state.ServerState;
import com.ap.chat.server.state.UserSession;

import java.io.EOFException;
import java.net.Socket;
import java.net.SocketException;

public class ClientHandler implements Runnable {
    private final Socket socket;
    private final ServerCommandDispatcher dispatcher;
    private final ServerState state;

    public ClientHandler(Socket socket, ServerCommandDispatcher dispatcher, ServerState state) {
        this.socket = socket;
        this.dispatcher = dispatcher;
        this.state = state;
    }

    @Override
    public void run() {
        ConnectionContext ctx = null;
        UserSession session = null;

        try {
            ctx = new ConnectionContext(socket);

            while (true) {
                Object obj = ctx.getIn().readObject();
                if (!(obj instanceof Packet)) continue;

                @SuppressWarnings("unchecked")
                Packet<Object> packet = (Packet<Object>) obj;

                ServerCommandDispatcher.Result result = dispatcher.dispatch(packet, ctx, session);
                if (result.session != null) session = result.session;

                if (result.response != null) {
                    if (session != null) {
                        session.send(result.response);
                    } else {
                        synchronized (ctx.getOut()) {
                            ctx.getOut().writeObject(result.response);
                            ctx.getOut().flush();
                        }
                    }
                }
            }
        } catch (EOFException | SocketException e) {
            // disconnected
        } catch (Exception e) {
            try {
                Packet<ErrorPayload> err = ExceptionManager.toErrorPacket(e);
                if (session != null) session.send(err);
            } catch (Exception ignored) {}
        } finally {
            // required: cleanup in finally
            try { if (session != null) state.onDisconnect(session); } catch (Exception ignored) {}
            if (ctx != null) {
                try { ctx.close(); } catch (Exception ignored) {}
            }
        }
    }
}
