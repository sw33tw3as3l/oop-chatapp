package com.ap.chat.server.repository;

import java.util.List;
import java.util.Optional;

public interface Repository<T> {
    void save(String id, T value);
    Optional<T> findById(String id);
    boolean exists(String id);
    void delete(String id);
    List<T> findAll();
}
