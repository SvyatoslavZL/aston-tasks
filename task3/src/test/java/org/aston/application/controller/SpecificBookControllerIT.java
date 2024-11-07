package org.aston.application.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.aston.application.BaseIT;
import org.aston.application.dto.BookTo;
import org.aston.application.service.CrudService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class SpecificBookControllerIT extends BaseIT {

    private final String specificBookControllerUrl = "/books/{id}";
    @Mock
    private CrudService<BookTo> bookServiceMock;
    @InjectMocks
    private SpecificBookController specificBookController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(specificBookController).build();
        when(bookServiceMock.get(testId)).thenReturn(Optional.ofNullable(testBookTo));
    }

    @Test
    void getBookById_returnsCorrectBook() throws Exception {
        mockMvc.perform(get(specificBookControllerUrl, testId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(testId))
                .andExpect(jsonPath("$.title").value(testTitle))
                .andExpect(jsonPath("$.genre").value(testGenre))
                .andExpect(jsonPath("$.year").value(testYear));
        verify(bookServiceMock).get(testId);
    }

    @Test
    void updateBook_updatesCorrectBook() throws Exception {
        objectMapper = new ObjectMapper();
        String bookJson = objectMapper.writeValueAsString(testBookTo);

        mockMvc.perform(patch(specificBookControllerUrl, testId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(bookJson)
        ).andExpect(status().isNoContent());
        verify(bookServiceMock).update(testId, testBookTo);
    }

    @Test
    void deleteBook_deletesCorrectBook() throws Exception {
        mockMvc.perform(delete(specificBookControllerUrl, testId))
                .andExpect(status().isNoContent());
    }
}