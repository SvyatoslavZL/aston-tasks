package org.aston.application.service;

import org.aston.application.dto.BookTo;
import org.aston.application.entity.Author;
import org.aston.application.entity.Book;
import org.aston.application.mapping.EntityMapper;
import org.aston.application.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

class BookServiceTest {

    private static final String TEST_FIRST_NAME = "testFirstName";
    private static final String TEST_LAST_NAME = "testLastName";
    private static final String TEST_TITLE = "testTitle";
    private static final String TEST_GENRE = "testGenre";
    private static final int TEST_YEAR = 1871;

    private Book book;
    private BookTo bookTo;
    private BookRepository bookRepositoryMock;
    private BookService bookService;

    @BeforeEach
    void setUp() {
        Author testAuthor = new Author(TEST_FIRST_NAME, TEST_LAST_NAME);
        book = new Book(testAuthor, TEST_TITLE, TEST_GENRE, TEST_YEAR);
        bookTo = EntityMapper.from(book);
        bookRepositoryMock = mock(BookRepository.class);
        when(bookRepositoryMock.find(any(Book.class))).thenReturn(Stream.of(book));
        bookService = new BookService(bookRepositoryMock);
    }

    @Test
    void getAll_shouldReturnAllBooks() {
        when(bookRepositoryMock.getAll()).thenReturn(List.of(book, book));

        Collection<BookTo> all = bookService.getAll();
        assertEquals(2, all.size());
        assertTrue(all.contains(bookTo));
        verify(bookRepositoryMock).getAll();
    }

    @Test
    void getById_shouldReturnCorrectBook() {
        Optional<BookTo> optional = bookService.get(1L);
        BookTo actualBookTo = optional.orElseThrow();
        assertEquals(bookTo, actualBookTo);
    }

    @Test
    void findByLoginAndPassword_shouldReturnCorrectBook() {
        Optional<BookTo> optional = bookService.find(TEST_TITLE, TEST_GENRE, TEST_YEAR);

        assertEquals(bookTo, optional.orElseThrow());
        verify(bookRepositoryMock).find(any(Book.class));
    }

    @Test
    void create_shouldInvokeCreateMethod() {
        bookService.create(bookTo);
        verify(bookRepositoryMock).create(any(Book.class));
    }

    @Test
    void update_shouldInvokeUpdateMethod() {
        bookService.update(bookTo);
        verify(bookRepositoryMock).update(any(Book.class));
    }

    @Test
    void delete_shouldInvokeDeleteMethod() {
        bookService.delete(bookTo);
        verify(bookRepositoryMock).delete(any(Book.class));
    }
}