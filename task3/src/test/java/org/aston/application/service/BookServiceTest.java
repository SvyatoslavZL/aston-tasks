package org.aston.application.service;

import org.aston.application.dto.BookTo;
import org.aston.application.entity.Book;
import org.aston.application.mapping.EntityMapper;
import org.aston.application.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class BookServiceTest {

    public static final long TEST_ID = 1L;
    public static final int TEST_ALL_SIZE = 2;
    private static final String TEST_TITLE = "testTitle";
    private static final String TEST_GENRE = "testGenre";
    private static final int TEST_YEAR = 1871;

    private Book book;
    private BookTo bookTo;
    private BookRepository bookRepositoryMock;
    private BookService bookService;

    @BeforeEach
    void setUp() {
        book = Book.builder()
                .title(TEST_TITLE)
                .genre(TEST_GENRE)
                .year(TEST_YEAR)
                .build();
        bookTo = EntityMapper.mapper.from(book);
        bookRepositoryMock = mock(BookRepository.class);
        bookService = new BookService(bookRepositoryMock);
    }

    @Test
    void getAll_shouldReturnAllBooks() {
        when(bookRepositoryMock.findAll()).thenReturn(List.of(book, book));

        List<BookTo> all = bookService.getAll();

        assertEquals(TEST_ALL_SIZE, all.size());
        assertTrue(all.contains(bookTo));
        verify(bookRepositoryMock).findAll();
    }

    @Test
    void getById_shouldReturnCorrectBook() {
        when(bookRepositoryMock.findById(TEST_ID)).thenReturn(Optional.of(book));

        Optional<BookTo> optional = bookService.get(TEST_ID);

        assertTrue(optional.isPresent());
        assertEquals(bookTo, optional.orElseThrow());
    }

    @Test
    void getByLoginAndPassword_shouldReturnCorrectBook() {
        when(bookRepositoryMock.findByTitleAndGenreAndYear(TEST_TITLE, TEST_GENRE, TEST_YEAR))
                .thenReturn(Optional.of(book));

        Optional<BookTo> optional = bookService.get(TEST_TITLE, TEST_GENRE, TEST_YEAR);

        assertTrue(optional.isPresent());
        assertEquals(bookTo, optional.orElseThrow());
        verify(bookRepositoryMock).findByTitleAndGenreAndYear(TEST_TITLE, TEST_GENRE, TEST_YEAR);
    }

    @Test
    void create_shouldReturnCreatedBook() {
        when(bookRepositoryMock.saveAndFlush(book)).thenReturn(book);

        BookTo actual = bookService.create(bookTo);

        assertEquals(bookTo, actual);
    }

    @Test
    void update_shouldInvokeUpdateMethodWithCorrectParameters() {
        bookService.update(TEST_ID, bookTo);
        verify(bookRepositoryMock).updateById(
                TEST_ID,
                TEST_TITLE,
                TEST_GENRE,
                TEST_YEAR
        );
    }

    @Test
    void delete_shouldInvokeDeleteMethodWithCorrectParameter() {
        bookService.delete(TEST_ID);
        verify(bookRepositoryMock).deleteById(TEST_ID);
    }
}