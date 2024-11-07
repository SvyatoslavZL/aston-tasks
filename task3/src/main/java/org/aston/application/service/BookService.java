package org.aston.application.service;

import lombok.AllArgsConstructor;
import org.aston.application.dto.BookTo;
import org.aston.application.entity.Book;
import org.aston.application.mapping.EntityMapper;
import org.aston.application.repository.BookRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional
public class BookService implements CrudService<BookTo> {

    private final BookRepository bookRepository;

    @Override
    public BookTo create(BookTo bookTo) {
        Book book = EntityMapper.mapper.from(bookTo);
        Book createdBook = bookRepository.saveAndFlush(book);
        return EntityMapper.mapper.from(createdBook);
    }

    @Override
    public List<BookTo> getAll() {
        return bookRepository.findAll()
                .stream()
                .map(EntityMapper.mapper::from)
                .toList();
    }

    @Override
    public Optional<BookTo> get(Long id) {
        return bookRepository.findById(id)
                .map(EntityMapper.mapper::from);
    }

    public Optional<BookTo> get(String title, String genre, int year) {
        return bookRepository.findByTitleAndGenreAndYear(title, genre, year)
                .map(EntityMapper.mapper::from);
    }

    @Override
    public void update(Long id, BookTo bookTo) {
        bookRepository.updateById(id,
                bookTo.getTitle(),
                bookTo.getGenre(),
                bookTo.getYear()
        );
    }

    @Override
    public void delete(Long id) {
        bookRepository.deleteById(id);
    }

}
