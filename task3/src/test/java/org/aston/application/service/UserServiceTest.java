package org.aston.application.service;

import org.aston.application.dto.Role;
import org.aston.application.dto.UserTo;
import org.aston.application.entity.User;
import org.aston.application.mapping.EntityMapper;
import org.aston.application.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class UserServiceTest {

    public static final long TEST_ID = 1L;
    public static final int TEST_ALL_SIZE = 2;
    private static final String TEST_LOGIN = "testLogin";
    private static final String TEST_PASSWORD = "testPassword";
    public static final Role TEST_ROLE = Role.USER;

    private User user;
    private UserTo userTo;
    private UserRepository userRepositoryMock;
    private UserService userService;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .id(TEST_ID)
                .login(TEST_LOGIN)
                .password(TEST_PASSWORD)
                .role(TEST_ROLE)
                .build();
        userTo = EntityMapper.mapper.from(user);
        userRepositoryMock = mock(UserRepository.class);
        userService = new UserService(userRepositoryMock);
    }

    @Test
    void getAll_shouldReturnAllUsers() {
        when(userRepositoryMock.findAll()).thenReturn(List.of(user, user));

        List<UserTo> all = userService.getAll();

        assertEquals(TEST_ALL_SIZE, all.size());
        assertTrue(all.contains(userTo));
        verify(userRepositoryMock).findAll();
    }

    @Test
    void getById_shouldReturnCorrectUser() {
        when(userRepositoryMock.findById(TEST_ID)).thenReturn(Optional.of(user));

        Optional<UserTo> optional = userService.get(TEST_ID);

        assertTrue(optional.isPresent());
        assertEquals(userTo, optional.orElseThrow());
    }

    @Test
    void getByLoginAndPassword_shouldReturnCorrectUser() {
        when(userRepositoryMock.findByLoginAndPassword(TEST_LOGIN, TEST_PASSWORD))
                .thenReturn(Optional.of(user));

        Optional<UserTo> optional = userService.get(TEST_LOGIN, TEST_PASSWORD);

        assertTrue(optional.isPresent());
        assertEquals(userTo, optional.orElseThrow());
        verify(userRepositoryMock).findByLoginAndPassword(TEST_LOGIN, TEST_PASSWORD);
    }

    @Test
    void create_shouldReturnCreatedUser() {
        when(userRepositoryMock.saveAndFlush(user)).thenReturn(user);

        UserTo actual = userService.create(userTo);

        assertEquals(userTo, actual);
    }

    @Test
    void update_shouldInvokeUpdateMethodWithCorrectParameters() {
        userService.update(TEST_ID, userTo);
        verify(userRepositoryMock).updateById(
                TEST_ID,
                TEST_LOGIN,
                TEST_PASSWORD,
                TEST_ROLE
        );
    }

    @Test
    void delete_shouldInvokeDeleteMethodWithCorrectParameter() {
        userService.delete(TEST_ID);
        verify(userRepositoryMock).deleteById(TEST_ID);
    }
}