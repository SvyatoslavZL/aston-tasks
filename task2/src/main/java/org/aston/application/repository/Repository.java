package org.aston.application.repository;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Stream;

public interface Repository<T> {

    Collection<T> getAll();

    Optional<T> get(long id);

    Stream<T> find(T pattern);

    void create(T entity);

    void update(T entity);

    void delete(T entity);
}
