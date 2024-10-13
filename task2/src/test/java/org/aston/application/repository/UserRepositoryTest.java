package org.aston.application.repository;

import org.aston.application.ConnectionPoolManager;
import org.aston.application.TestContainer;
import org.aston.application.dto.Role;
import org.aston.application.entity.User;
import org.aston.application.exception.DaoException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class UserRepositoryTest extends TestContainer implements ConnectionPoolManager {

    private static final String TEST_LOGIN = "testLogin";
    private static final String ANOTHER_TEST_LOGIN = "anotherTestLogin";
    private static final String TEST_PASSWORD = "testPassword";
    private static final String ANOTHER_TEST_PASSWORD = "anotherTestPassword";

    private UserRepository userRepository;
    private User testUser;

    @BeforeEach
    void setUp() {
        userRepository = new UserRepository();
        testUser = new User(TEST_LOGIN, TEST_PASSWORD, Role.USER);
        userRepository.create(testUser);
    }

    @AfterEach
    void tearDown() {
        userRepository.delete(testUser);
    }

    @Test
    void getAll_shouldReturnMoreUsersThanZero() {
        long count = userRepository.getAll().size();
        assertTrue(count > 0);
    }

    @Test
    void getById_shouldReturnCorrectUser() {
        User user = null;
        if (userRepository.get(1L).isPresent()) {
            user = userRepository.get(1L).get();
        }

        assertNotNull(user);
        assertEquals(1L, user.getId());
        assertEquals(Role.ADMIN, user.getRole());
    }

    @Test
    void findByLoginAndPassword_shouldReturnCorrectUser() {
        User pattern = new User(TEST_LOGIN, TEST_PASSWORD);
        Stream<User> userStream = userRepository.find(pattern);
        assertEquals(testUser, userStream.findFirst().orElseThrow());
    }

    @Test
    void create_shouldCreateCorrectUser() {
        Long testUserId = testUser.getId();

        User actualUser = null;
        if (userRepository.get(testUserId).isPresent()) {
            actualUser = userRepository.get(testUserId).get();
        }

        assertNotNull(actualUser);
        assertEquals(testUser, actualUser);
    }

    @Test
    void update_shouldUpdateSpecificUser() {
        Long testUserId = testUser.getId();

        testUser.setLogin(ANOTHER_TEST_LOGIN);
        testUser.setPassword(ANOTHER_TEST_PASSWORD);
        testUser.setRole(Role.ADMIN);
        userRepository.update(testUser);

        User actualUser = null;
        if (userRepository.get(testUserId).isPresent()) {
            actualUser = userRepository.get(testUserId).get();
        }

        assertNotNull(actualUser);
        assertEquals(ANOTHER_TEST_LOGIN, actualUser.getLogin());
        assertEquals(ANOTHER_TEST_PASSWORD, actualUser.getPassword());
        assertEquals(Role.ADMIN, actualUser.getRole());

    }

    @Test
    void delete_shouldDeleteSpecificUser() {
        Long testUserId = testUser.getId();

        User userBeforeDeletion = null;
        if (userRepository.get(testUserId).isPresent()) {
            userBeforeDeletion = userRepository.get(testUserId).get();
        }
        assertNotNull(userBeforeDeletion);

        userRepository.delete(testUser);
        assertThrows(DaoException.class, () -> userRepository.get(testUserId).get());
    }

}