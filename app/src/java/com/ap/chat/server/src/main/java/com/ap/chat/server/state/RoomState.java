package com.ap.chat.server.state;

import com.ap.chat.common.model.Message;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.locks.ReentrantLock;

public class RoomState {
    private final String name;
    private final ReentrantLock lock = new ReentrantLock(true);
    private final Set<String> members = new HashSet<>();
    private final List<Message> history = new ArrayList<>();

    public RoomState(String name) {
        this.name = name;
    }

    public String getName() { return name; }
    public ReentrantLock getLock() { return lock; }

    public void addMember(String username) {
        lock.lock();
        try { members.add(username); }
        finally { lock.unlock(); }
    }

    public void removeMember(String username) {
        lock.lock();
        try { members.remove(username); }
        finally { lock.unlock(); }
    }

    public List<String> snapshotMembers() {
        lock.lock();
        try { return new ArrayList<>(members); }
        finally { lock.unlock(); }
    }

    public void appendMessage(Message m) {
        lock.lock();
        try { history.add(m); }
        finally { lock.unlock(); }
    }

    public List<Message> lastMessages(int n) {
        lock.lock();
        try {
            int size = history.size();
            int from = Math.max(0, size - n);
            return new ArrayList<>(history.subList(from, size));
        } finally {
            lock.unlock();
        }
    }
}
