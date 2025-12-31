package com.ap.chat.client.net;

import java.io.Closeable;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ConnectionContext implements Closeable {
    private final Socket socket;
    private final ObjectOutputStream out;
    private final ObjectInputStream in;

    public ConnectionContext(Socket socket) throws Exception {
        this.socket = socket;
        // using flushing 
        this.out = new ObjectOutputStream(socket.getOutputStream());
        this.out.flush();
        this.in = new ObjectInputStream(socket.getInputStream());
    }

    public Socket getSocket() { return socket; }

    public ObjectOutputStream getOut() { return out; }

    public ObjectInputStream getIn() { return in; }

    @Override
    public void close() {
        try { in.close(); } catch (Exception ignored) {}

        try { out.close(); } catch (Exception ignored) {}
        
        try { socket.close(); } catch (Exception ignored) {}
    }
}