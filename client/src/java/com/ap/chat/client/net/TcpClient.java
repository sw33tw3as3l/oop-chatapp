package com.ap.chat.client.net;

import com.ap.chat.common.protocol.Packet;

import java.net.Socket;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

public class TcpClient {
    private final String host;
    private final int port;
    private ConnectionContext ctx;
    private final AtomicBoolean running = new AtomicBoolean(true);

    private final Map<String, ResponseHandler> pending = new ConcurrentHashMap<>();

    public TcpClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void connect() throws Exception {
        Socket socket = new Socket(host, port);
        socket.setTcpNoDelay(true);
        this.ctx = new ConnectionContext(socket);
        System.out.println("Connected to " + host + ":" + port);
    }

    public ConnectionContext getCtx() { return ctx; }
    public boolean isRunning() { return running.get(); }

    public void stop() { running.set(false); }

    public void close() {
        stop();
        if (ctx != null) ctx.close();
    }

    public String nextRequestId() {
        return UUID.randomUUID().toString();
    }

    public void registerHandler(String requestId, ResponseHandler handler) {
        if (requestId == null) return;
        pending.put(requestId, handler);
    }

    public ResponseHandler takeHandler(String requestId) {
        if (requestId == null) return null;
        return pending.remove(requestId);
    }

    public void send(Packet<?> packet) throws Exception {
        synchronized (ctx.getOut()) {
            ctx.getOut().writeObject(packet);
            ctx.getOut().flush();
        }
    }
}
