package org.aston.application.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.aston.application.BaseIT;
import org.aston.application.dto.Role;
import org.aston.application.dto.UserTo;
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
class UserControllerIT extends BaseIT {

    private final String userControllerUrl = "/users";
    @Mock
    private CrudService<UserTo> userServiceMock;
    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    void getUsers_returnsCorrectUsers() throws Exception {
        ArrayList<UserTo> expectedUsers = new ArrayList<>();
        expectedUsers.add(new UserTo(testId, testLogin, testPassword, Role.USER, null));
        expectedUsers.add(new UserTo(testId, testLogin, testPassword, Role.USER, null));
        when(userServiceMock.getAll()).thenReturn(expectedUsers);

        mockMvc.perform(get(userControllerUrl))
                .andExpect(status().isOk());
        List<UserTo> actualUsers = userController.getUsers();
        Assertions.assertEquals(expectedUsers, actualUsers);
    }

    @Test
    void createUser_createsCorrectUser() throws Exception {
        when(userServiceMock.create(testUserTo)).thenReturn(testUserTo);
        objectMapper = new ObjectMapper();
        String userJson = objectMapper.writeValueAsString(testUserTo);

        mockMvc.perform(post(userControllerUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(testId))
                .andExpect(jsonPath("$.login").value(testLogin))
                .andExpect(jsonPath("$.password").value(testPassword));
        verify(userServiceMock).create(testUserTo);
    }
}