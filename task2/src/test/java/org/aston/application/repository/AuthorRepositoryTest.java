package org.aston.application.repository;

import org.aston.application.ConnectionPoolManager;
import org.aston.application.TestContainer;
import org.aston.application.entity.Author;
import org.aston.application.exception.DaoException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class AuthorRepositoryTest extends TestContainer implements ConnectionPoolManager {

    private static final String TEST_FIRST_NAME = "testFirstName";
    private static final String ANOTHER_TEST_FIRST_NAME = "anotherTestFirstName";
    private static final String TEST_LAST_NAME = "testLastName";
    private static final String ANOTHER_TEST_LAST_NAME = "anotherTestLastName";
    private AuthorRepository authorRepository;
    private Author testAuthor;

    @BeforeEach
    void setUp() {
        authorRepository = new AuthorRepository();
        testAuthor = new Author(TEST_FIRST_NAME, TEST_LAST_NAME);
        authorRepository.create(testAuthor);
    }

    @AfterEach
    void tearDown() {
        authorRepository.delete(testAuthor);
    }

    @Test
    void getAll_shouldReturnMoreAuthorsThanZero() {
        long count = authorRepository.getAll().size();
        assertTrue(count > 0);
    }

    @Test
    void getById_shouldReturnCorrectAuthor() {
        Author author = null;
        if (authorRepository.get(1L).isPresent()) {
            author = authorRepository.get(1L).get();
        }

        assertNotNull(author);
        assertEquals(1L, author.getId());
        assertEquals("Edmond", author.getFirstName());
        assertEquals("Hamilton", author.getLastName());
    }

    @Test
    void findByName_shouldReturnCorrectAuthor() {
        Author pattern = new Author(TEST_FIRST_NAME, TEST_LAST_NAME);
        Stream<Author> authorStream = authorRepository.find(pattern);
        assertEquals(testAuthor, authorStream.findFirst().orElseThrow());
    }

    @Test
    void create_shouldCreateCorrectAuthor() {
        Long testAuthorId = testAuthor.getId();

        Author actualAuthor = null;
        if (authorRepository.get(testAuthorId).isPresent()) {
            actualAuthor = authorRepository.get(testAuthorId).get();
        }

        assertNotNull(actualAuthor);
        assertEquals(testAuthor, actualAuthor);
    }

    @Test
    void update_shouldUpdateSpecificAuthor() {
        Long testAuthorId = testAuthor.getId();

        testAuthor.setFirstName(ANOTHER_TEST_FIRST_NAME);
        testAuthor.setLastName(ANOTHER_TEST_LAST_NAME);
        authorRepository.update(testAuthor);

        Author actualAuthor = null;
        if (authorRepository.get(testAuthorId).isPresent()) {
            actualAuthor = authorRepository.get(testAuthorId).get();
        }

        assertNotNull(actualAuthor);
        assertEquals(ANOTHER_TEST_FIRST_NAME, actualAuthor.getFirstName());
        assertEquals(ANOTHER_TEST_LAST_NAME, actualAuthor.getLastName());
    }

    @Test
    void delete_shouldDeleteSpecificAuthor() {
        Long testAuthorId = testAuthor.getId();

        Author authorBeforeDeletion = null;
        if (authorRepository.get(testAuthorId).isPresent()) {
            authorBeforeDeletion = authorRepository.get(testAuthorId).get();
        }
        assertNotNull(authorBeforeDeletion);

        authorRepository.delete(testAuthor);
        assertThrows(DaoException.class, () -> authorRepository.get(testAuthorId).get());
    }

}