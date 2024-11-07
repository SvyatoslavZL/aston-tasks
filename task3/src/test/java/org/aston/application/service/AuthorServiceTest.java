package org.aston.application.service;

import org.aston.application.dto.AuthorTo;
import org.aston.application.entity.Author;
import org.aston.application.mapping.EntityMapper;
import org.aston.application.repository.AuthorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class AuthorServiceTest {

    public static final long TEST_ID = 1L;
    public static final int TEST_ALL_SIZE = 2;
    private static final String TEST_FIRST_NAME = "testFirstName";
    private static final String TEST_LAST_NAME = "testLastName";

    private Author author;
    private AuthorTo authorTo;
    private AuthorRepository authorRepositoryMock;
    private AuthorService authorService;

    @BeforeEach
    void setUp() {
        author = Author.builder()
                .id(TEST_ID)
                .firstName(TEST_FIRST_NAME)
                .lastName(TEST_LAST_NAME)
                .build();
        authorTo = EntityMapper.mapper.from(author);
        authorRepositoryMock = mock(AuthorRepository.class);
        authorService = new AuthorService(authorRepositoryMock);
    }

    @Test
    void getAll_shouldReturnAllAuthors() {
        when(authorRepositoryMock.findAll()).thenReturn(List.of(author, author));

        List<AuthorTo> all = authorService.getAll();

        assertEquals(TEST_ALL_SIZE, all.size());
        assertTrue(all.contains(authorTo));
        verify(authorRepositoryMock).findAll();
    }

    @Test
    void getById_shouldReturnCorrectAuthor() {
        when(authorRepositoryMock.findById(TEST_ID)).thenReturn(Optional.of(author));

        Optional<AuthorTo> optional = authorService.get(TEST_ID);

        assertTrue(optional.isPresent());
        assertEquals(authorTo, optional.orElseThrow());
    }

    @Test
    void getByFirstNameAndLastName_shouldReturnCorrectAuthor() {
        when(authorRepositoryMock.findByFirstNameAndLastName(TEST_FIRST_NAME, TEST_LAST_NAME))
                .thenReturn(Optional.of(author));

        Optional<AuthorTo> optional = authorService.get(TEST_FIRST_NAME, TEST_LAST_NAME);

        assertTrue(optional.isPresent());
        assertEquals(authorTo, optional.orElseThrow());
        verify(authorRepositoryMock).findByFirstNameAndLastName(TEST_FIRST_NAME, TEST_LAST_NAME);
    }

    @Test
    void create_shouldReturnCreatedAuthor() {
        when(authorRepositoryMock.saveAndFlush(author)).thenReturn(author);

        AuthorTo actual = authorService.create(authorTo);

        assertEquals(authorTo, actual);
    }

    @Test
    void update_shouldInvokeUpdateMethodWithCorrectParameters() {
        authorService.update(TEST_ID, authorTo);
        verify(authorRepositoryMock).updateById(
                TEST_ID,
                TEST_FIRST_NAME,
                TEST_LAST_NAME
        );
    }

    @Test
    void delete_shouldInvokeDeleteMethodWithCorrectParameter() {
        authorService.delete(TEST_ID);
        verify(authorRepositoryMock).deleteById(TEST_ID);
    }
}