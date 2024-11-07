package org.aston.application.service;

import java.util.List;
import java.util.Optional;

public interface CrudService<T> {

    T create(T entity);

    List<T> getAll();

    Optional<T> get(Long id);

    void update(Long id, T entity);

    void delete(Long id);

}
