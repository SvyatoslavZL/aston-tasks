package org.aston.application.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.aston.application.BaseIT;
import org.aston.application.dto.AuthorTo;
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
class SpecificAuthorControllerIT extends BaseIT {

    private final String specificAuthorControllerUrl = "/authors/{id}";
    @Mock
    private CrudService<AuthorTo> authorServiceMock;
    @InjectMocks
    private SpecificAuthorController specificAuthorController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(specificAuthorController).build();
        when(authorServiceMock.get(testId)).thenReturn(Optional.ofNullable(testAuthorTo));
    }

    @Test
    void getAuthorById_returnsCorrectAuthor() throws Exception {
        mockMvc.perform(get(specificAuthorControllerUrl, testId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(testId))
                .andExpect(jsonPath("$.firstName").value(testFirstName))
                .andExpect(jsonPath("$.lastName").value(testLastName));
        verify(authorServiceMock).get(testId);
    }

    @Test
    void updateAuthor_updatesCorrectAuthor() throws Exception {
        objectMapper = new ObjectMapper();
        String authorJson = objectMapper.writeValueAsString(testAuthorTo);

        mockMvc.perform(patch(specificAuthorControllerUrl, testId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(authorJson)
        ).andExpect(status().isNoContent());
        verify(authorServiceMock).update(testId, testAuthorTo);
    }

    @Test
    void deleteAuthor_deletesCorrectAuthor() throws Exception {
        mockMvc.perform(delete(specificAuthorControllerUrl, testId))
                .andExpect(status().isNoContent());
    }
}