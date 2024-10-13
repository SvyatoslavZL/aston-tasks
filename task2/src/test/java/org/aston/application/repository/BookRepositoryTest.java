package org.aston.application.repository;

import org.aston.application.ConnectionPoolManager;
import org.aston.application.TestContainer;
import org.aston.application.entity.Author;
import org.aston.application.entity.Book;
import org.aston.application.exception.DaoException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class BookRepositoryTest extends TestContainer implements ConnectionPoolManager {

    private static final String TEST_FIRST_NAME = "testFirstName";
    private static final String TEST_LAST_NAME = "testLastName";
    private static final String TEST_TITLE = "testTitle";
    private static final String ANOTHER_TEST_TITLE = "anotherTestTitle";
    private static final String TEST_GENRE = "testGenre";
    private static final String ANOTHER_TEST_GENRE = "anotherTestGenre";
    private static final int TEST_YEAR = 1871;
    private static final int ANOTHER_TEST_YEAR = 2005;

    private final AuthorRepository authorRepository = new AuthorRepository();
    private final BookRepository bookRepository = new BookRepository(authorRepository);
    private final Author testAuthor = new Author(TEST_FIRST_NAME, TEST_LAST_NAME);
    private Book testBook;

    @BeforeEach
    void setUp() {
        authorRepository.create(testAuthor);
        testBook = new Book(testAuthor, TEST_TITLE, TEST_GENRE, TEST_YEAR);
        bookRepository.create(testBook);
    }

    @AfterEach
    void tearDown() {
        bookRepository.delete(testBook);
    }

    @Test
    void getAll_shouldReturnMoreBooksThanZero() {
        long count = bookRepository.getAll().size();
        assertTrue(count > 0);
    }

    @Test
    void getById_shouldReturnCorrectBook() {
        Book book = null;
        if (bookRepository.get(1L).isPresent()) {
            book = bookRepository.get(1L).get();
        }

        assertNotNull(book);
        assertEquals(1L, book.getId());
        assertEquals("The Star Kings", book.getTitle());
    }

    @Test
    void findByFields_shouldReturnCorrectBook() {
        Book pattern = new Book(TEST_TITLE, TEST_GENRE, TEST_YEAR);
        Stream<Book> bookStream = bookRepository.find(pattern);
        assertEquals(testBook, bookStream.findFirst().orElseThrow());
    }

    @Test
    void create_shouldCreateCorrectBook() {
        Long testBookId = testBook.getId();

        Book actualBook = null;
        if (bookRepository.get(testBookId).isPresent()) {
            actualBook = bookRepository.get(testBookId).get();
        }

        assertNotNull(actualBook);
        assertEquals(testBook, actualBook);
    }

    @Test
    void update_shouldUpdateSpecificBook() {
        Long testBookId = testBook.getId();

        testBook.setTitle(ANOTHER_TEST_TITLE);
        testBook.setGenre(ANOTHER_TEST_GENRE);
        testBook.setYear(ANOTHER_TEST_YEAR);
        bookRepository.update(testBook);

        Book actualBook = null;
        if (bookRepository.get(testBookId).isPresent()) {
            actualBook = bookRepository.get(testBookId).get();
        }

        assertNotNull(actualBook);
        assertEquals(ANOTHER_TEST_TITLE, actualBook.getTitle());
        assertEquals(ANOTHER_TEST_GENRE, actualBook.getGenre());
        assertEquals(ANOTHER_TEST_YEAR, actualBook.getYear());
    }

    @Test
    void delete_shouldDeleteSpecificBook() {
        Long testBookId = testBook.getId();

        Book bookBeforeDeletion = null;
        if (bookRepository.get(testBookId).isPresent()) {
            bookBeforeDeletion = bookRepository.get(testBookId).get();
        }
        assertNotNull(bookBeforeDeletion);

        bookRepository.delete(testBook);
        assertThrows(DaoException.class, () -> bookRepository.get(testBookId).get());
    }

}