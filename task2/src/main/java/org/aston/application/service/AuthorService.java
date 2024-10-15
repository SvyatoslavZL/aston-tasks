package org.aston.application.service;

import org.aston.application.dto.AuthorTo;
import org.aston.application.entity.Author;
import org.aston.application.mapping.EntityMapper;
import org.aston.application.repository.Repository;

import java.util.Collection;
import java.util.Optional;

public class AuthorService {

    private final Repository<Author> authorRepository;

    public AuthorService(Repository<Author> authorRepository) {
        this.authorRepository = authorRepository;
    }

    public void create(AuthorTo authorTo) {
        authorRepository.create(EntityMapper.from(authorTo));
    }

    public Collection<AuthorTo> getAll() {
        return authorRepository.getAll().stream().map(EntityMapper::from).toList();
    }

    public Optional<AuthorTo> get(long id) {
        Author authorPattern = new Author(id);
        return authorRepository.find(authorPattern).map(EntityMapper::from).findFirst();
    }

    public Optional<AuthorTo> find(String firstName, String lastName) {
        Author authorPattern = new Author(firstName, lastName);
        return authorRepository.find(authorPattern).map(EntityMapper::from).findAny();
    }

    public void update(AuthorTo authorTo) {
        authorRepository.update(EntityMapper.from(authorTo));
    }

    public void delete(AuthorTo authorTo) {
        authorRepository.delete(EntityMapper.from(authorTo));
    }
}
