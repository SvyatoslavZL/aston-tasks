package org.aston.application.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.aston.application.BaseIT;
import org.aston.application.dto.UserTo;
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
class SpecificUserControllerIT extends BaseIT {

    private final String specificUserControllerUrl = "/users/{id}";
    @Mock
    private CrudService<UserTo> userServiceMock;
    @InjectMocks
    private SpecificUserController specificUserController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(specificUserController).build();
        when(userServiceMock.get(testId)).thenReturn(Optional.ofNullable(testUserTo));
    }

    @Test
    void getUserById_returnsCorrectUser() throws Exception {
        mockMvc.perform(get(specificUserControllerUrl, testId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(testId))
                .andExpect(jsonPath("$.login").value(testLogin))
                .andExpect(jsonPath("$.password").value(testPassword));
        verify(userServiceMock).get(testId);
    }

    @Test
    void updateUser_updatesCorrectUser() throws Exception {
        objectMapper = new ObjectMapper();
        String userJson = objectMapper.writeValueAsString(testUserTo);

        mockMvc.perform(patch(specificUserControllerUrl, testId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJson)
        ).andExpect(status().isNoContent());
        verify(userServiceMock).update(testId, testUserTo);
    }

    @Test
    void deleteUser_deletesCorrectUser() throws Exception {
        mockMvc.perform(delete(specificUserControllerUrl, testId))
                .andExpect(status().isNoContent());
    }
}