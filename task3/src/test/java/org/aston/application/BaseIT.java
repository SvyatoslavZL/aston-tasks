package org.aston.application;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.aston.application.dto.AuthorTo;
import org.aston.application.dto.BookTo;
import org.aston.application.dto.Role;
import org.aston.application.dto.UserTo;
import org.aston.application.entity.Author;
import org.springframework.test.web.servlet.MockMvc;

public class BaseIT extends TestContainer {
    protected final String testFirstName;
    protected final String testLastName;
    protected final String testLogin;
    protected final String testPassword;
    protected final String testTitle;
    protected final String testGenre;
    protected final int testYear;
    protected final Long testId;
    protected final UserTo testUserTo;
    protected final AuthorTo testAuthorTo;
    protected final Author testBookAuthor;
    protected final BookTo testBookTo;
    protected MockMvc mockMvc;
    protected ObjectMapper objectMapper;

    protected BaseIT() {
        testId = 1L;
        testLogin = "testLogin";
        testPassword = "testPassword";
        testUserTo = new UserTo(testId, testLogin, testPassword, Role.USER, null);
        testFirstName = "Edmond";
        testLastName = "Hamilton";
        testAuthorTo = new AuthorTo(testId, testFirstName, testLastName, null);
        testTitle = "testTitle";
        testGenre = "testGenre";
        testYear = 8291;
        testBookAuthor = new Author(
                testAuthorTo.getId(),
                testAuthorTo.getFirstName(),
                testAuthorTo.getLastName(),
                null
        );
        testBookTo = new BookTo(testId, testBookAuthor, testTitle, testGenre, testYear, null);
    }
}
