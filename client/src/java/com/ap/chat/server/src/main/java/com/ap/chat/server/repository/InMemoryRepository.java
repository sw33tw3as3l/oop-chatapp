package com.ap.chat.server.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryRepository<T> implements Repository<T> {
    private final ConcurrentHashMap<String, T> map = new ConcurrentHashMap<>();

    @Override
    public void save(String id, T value) { map.put(id, value); }

    @Override
    public Optional<T> findById(String id) { return Optional.ofNullable(map.get(id)); }

    @Override
    public boolean exists(String id) { return map.containsKey(id); }

    @Override
    public void delete(String id) { map.remove(id); }

    @Override
    public List<T> findAll() { return new ArrayList<>(map.values()); }
}
