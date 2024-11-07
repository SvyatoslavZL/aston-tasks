package org.aston.application.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.aston.application.BaseIT;
import org.aston.application.dto.AuthorTo;
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
class AuthorControllerIT extends BaseIT {

    private final String authorControllerUrl = "/authors";
    @Mock
    private CrudService<AuthorTo> authorServiceMock;
    @InjectMocks
    private AuthorController authorController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(authorController).build();
    }

    @Test
    void getAuthors_returnsCorrectAuthors() throws Exception {
        ArrayList<AuthorTo> expectedAuthors = new ArrayList<>();
        expectedAuthors.add(new AuthorTo(testId, testFirstName, testLastName, null));
        expectedAuthors.add(new AuthorTo(testId, testFirstName, testLastName, null));
        when(authorServiceMock.getAll()).thenReturn(expectedAuthors);

        mockMvc.perform(get(authorControllerUrl))
                .andExpect(status().isOk());
        List<AuthorTo> actualAuthors = authorController.getAuthors();
        Assertions.assertEquals(expectedAuthors, actualAuthors);
    }

    @Test
    void createAuthor_createsCorrectAuthor() throws Exception {
        when(authorServiceMock.create(testAuthorTo)).thenReturn(testAuthorTo);
        objectMapper = new ObjectMapper();
        String authorJson = objectMapper.writeValueAsString(testAuthorTo);

        mockMvc.perform(post(authorControllerUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authorJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(testId))
                .andExpect(jsonPath("$.firstName").value(testFirstName))
                .andExpect(jsonPath("$.lastName").value(testLastName));
        verify(authorServiceMock).create(testAuthorTo);
    }

}