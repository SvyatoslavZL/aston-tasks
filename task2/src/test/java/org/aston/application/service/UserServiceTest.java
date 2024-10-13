package org.aston.application.service;

import org.aston.application.dto.Role;
import org.aston.application.dto.UserTo;
import org.aston.application.entity.User;
import org.aston.application.mapping.EntityMapper;
import org.aston.application.repository.UserRepository;
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

class UserServiceTest {

    private static final String TEST_LOGIN = "testLogin";
    private static final String TEST_PASSWORD = "testPassword";

    private User user;
    private UserTo userTo;
    private UserRepository userRepositoryMock;
    private UserService userService;

    @BeforeEach
    void setUp() {
        user = new User(1L, TEST_LOGIN, TEST_PASSWORD, Role.USER);
        userTo = EntityMapper.from(user);
        userRepositoryMock = mock(UserRepository.class);
        when(userRepositoryMock.find(any(User.class))).thenReturn(Stream.of(user));
        userService = new UserService(userRepositoryMock);
    }

    @Test
    void getAll_shouldReturnAllUsers() {
        when(userRepositoryMock.getAll()).thenReturn(List.of(user, user));

        Collection<UserTo> all = userService.getAll();
        assertEquals(2, all.size());
        assertTrue(all.contains(userTo));
        verify(userRepositoryMock).getAll();
    }

    @Test
    void getById_shouldReturnCorrectUser() {
        Optional<UserTo> optional = userService.get(1L);
        UserTo actualUserTo = optional.orElseThrow();
        assertEquals(userTo, actualUserTo);
    }

    @Test
    void findByLoginAndPassword_shouldReturnCorrectUser() {
        Optional<UserTo> optional = userService.find(TEST_LOGIN, TEST_PASSWORD);

        assertEquals(userTo, optional.orElseThrow());
        verify(userRepositoryMock).find(any(User.class));
    }

    @Test
    void create_shouldInvokeCreateMethod() {
        userService.create(userTo);
        verify(userRepositoryMock).create(any(User.class));
    }

    @Test
    void update_shouldInvokeUpdateMethod() {
        userService.update(userTo);
        verify(userRepositoryMock).update(any(User.class));
    }

    @Test
    void delete_shouldInvokeDeleteMethod() {
        userService.delete(userTo);
        verify(userRepositoryMock).delete(any(User.class));
    }
}