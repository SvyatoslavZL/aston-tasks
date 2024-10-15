package org.aston.application.servlets;

import jakarta.servlet.ServletException;
import org.aston.application.BaseIT;
import org.aston.application.dto.Role;
import org.aston.application.dto.UserTo;
import org.aston.application.service.UserService;
import org.aston.application.util.Key;
import org.aston.application.util.NanoSpring;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class SignUpServletIT extends BaseIT {

    private final SignUpServlet signUp = NanoSpring.find(SignUpServlet.class);
    private final UserService userService = NanoSpring.find(UserService.class);

    @Test
    void doGet_shouldGetCorrectPage() throws ServletException, IOException {
        signUp.doGet(request, response);
        verify(request).getRequestDispatcher("WEB-INF/sign-up.jsp");
    }

    @Test
    void doPost_shouldSendRedirectToUserList() throws IOException {
        when(request.getParameter(Key.LOGIN)).thenReturn(testLogin);
        when(request.getParameter(Key.PASSWORD)).thenReturn(testPassword);
        when(request.getParameter(Key.ROLE)).thenReturn(Role.USER.toString());

        signUp.doPost(request, response);
        verify(response).sendRedirect(Key.USER_LIST);

        Optional<UserTo> createdTestUserTo = userService.find(testLogin, testPassword);
        assertNotNull(createdTestUserTo);
        createdTestUserTo.ifPresent(userService::delete);
    }

}