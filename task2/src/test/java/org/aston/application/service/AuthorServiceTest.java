package org.aston.application.service;

import org.aston.application.dto.AuthorTo;
import org.aston.application.entity.Author;
import org.aston.application.mapping.EntityMapper;
import org.aston.application.repository.AuthorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AuthorServiceTest {

    private static final String TEST_FIRST_NAME = "testFirstName";
    private static final String TEST_LAST_NAME = "testLastName";

    private Author author;
    private AuthorTo authorTo;
    private AuthorRepository authorRepositoryMock;
    private AuthorService authorService;

    @BeforeEach
    void setUp() {
        author = new Author(1L, TEST_FIRST_NAME, TEST_LAST_NAME);
        authorTo = EntityMapper.from(author);
        authorRepositoryMock = mock(AuthorRepository.class);
        when(authorRepositoryMock.find(any(Author.class))).thenReturn(Stream.of(author));
        authorService = new AuthorService(authorRepositoryMock);
    }

    @Test
    void getAll_shouldReturnAllAuthors() {
        when(authorRepositoryMock.getAll()).thenReturn(List.of(author, author));

        Collection<AuthorTo> all = authorService.getAll();
        assertEquals(2, all.size());
        assertTrue(all.contains(authorTo));
        verify(authorRepositoryMock).getAll();
    }

    @Test
    void getById_shouldReturnCorrectAuthor() {
        Optional<AuthorTo> optional = authorService.get(1L);
        AuthorTo actualTo = optional.orElseThrow();
        assertEquals(authorTo, actualTo);
    }

    @Test
    void findByName_shouldReturnCorrectAuthor() {
        Optional<AuthorTo> optional = authorService.find(TEST_FIRST_NAME, TEST_LAST_NAME);

        assertEquals(authorTo, optional.orElseThrow());
        verify(authorRepositoryMock).find(any(Author.class));
    }

    @Test
    void create_shouldInvokeCreateMethod() {
        authorService.create(authorTo);
        verify(authorRepositoryMock).create(any(Author.class));
    }

    @Test
    void update_shouldInvokeUpdateMethod() {
        authorService.update(authorTo);
        verify(authorRepositoryMock).update(any(Author.class));
    }

    @Test
    void delete_shouldInvokeDeleteMethod() {
        authorService.delete(authorTo);
        verify(authorRepositoryMock).delete(any(Author.class));
    }
}