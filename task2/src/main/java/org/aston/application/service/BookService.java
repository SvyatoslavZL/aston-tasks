package org.aston.application.service;

import org.aston.application.dto.BookTo;
import org.aston.application.entity.Book;
import org.aston.application.mapping.EntityMapper;
import org.aston.application.repository.Repository;

import java.util.Collection;
import java.util.Optional;

public class BookService {

    private final Repository<Book> bookRepository;

    public BookService(Repository<Book> bookRepository) {
        this.bookRepository = bookRepository;
    }

    public void create(BookTo bookTo) {
        bookRepository.create(EntityMapper.from(bookTo));
    }

    public Collection<BookTo> getAll() {
        return bookRepository.getAll().stream().map(EntityMapper::from).toList();
    }

    public Optional<BookTo> get(long id) {
        Book bookPattern = new Book(id);
        return bookRepository.find(bookPattern).map(EntityMapper::from).findFirst();
    }

    public Optional<BookTo> find(String title, String genre, int year) {
        Book bookPattern = new Book(title, genre, year);
        return bookRepository.find(bookPattern).map(EntityMapper::from).findAny();
    }

    public void update(BookTo bookTo) {
        bookRepository.update(EntityMapper.from(bookTo));
    }

    public void delete(BookTo bookTo) {
        bookRepository.delete(EntityMapper.from(bookTo));
    }
}
