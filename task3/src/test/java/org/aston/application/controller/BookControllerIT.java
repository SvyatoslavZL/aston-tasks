package org.aston.application.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.aston.application.BaseIT;
import org.aston.application.dto.BookTo;
import org.aston.application.service.CrudService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class BookControllerIT extends BaseIT {

    private final String bookControllerUrl = "/books";
    @Mock
    private CrudService<BookTo> bookServiceMock;
    @InjectMocks
    private BookController bookController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(bookController).build();
    }

    @Test
    void getBooks_returnsCorrectBooks() throws Exception {
        ArrayList<BookTo> expectedBooks = new ArrayList<>();
        expectedBooks.add(new BookTo(testId, testBookAuthor, testTitle, testGenre, testYear, null));
        expectedBooks.add(new BookTo(testId, testBookAuthor, testTitle, testGenre, testYear, null));
        when(bookServiceMock.getAll()).thenReturn(expectedBooks);

        mockMvc.perform(get(bookControllerUrl))
                .andExpect(status().isOk());
        List<BookTo> actualBooks = bookController.getBooks();
        Assertions.assertEquals(expectedBooks, actualBooks);
    }

    @Test
    void createBook_createsCorrectBook() throws Exception {
        when(bookServiceMock.create(testBookTo)).thenReturn(testBookTo);
        objectMapper = new ObjectMapper();
        String userJson = objectMapper.writeValueAsString(testBookTo);

        mockMvc.perform(post(bookControllerUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(testId))
                .andExpect(jsonPath("$.title").value(testTitle))
                .andExpect(jsonPath("$.genre").value(testGenre))
                .andExpect(jsonPath("$.year").value(testYear));
        verify(bookServiceMock).create(testBookTo);
    }
}