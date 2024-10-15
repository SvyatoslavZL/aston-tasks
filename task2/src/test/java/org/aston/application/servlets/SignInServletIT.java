package org.aston.application.servlets;

import jakarta.servlet.ServletException;
import org.aston.application.BaseIT;
import org.aston.application.dto.UserTo;
import org.aston.application.util.Key;
import org.aston.application.util.NanoSpring;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class SignInServletIT extends BaseIT {

    private static final String CORRECT_LOGIN = "Alex";
    private static final String CORRECT_PASSWORD = "admin";
    private final SignInServlet signIn = NanoSpring.find(SignInServlet.class);

    @Test
    void doGet_shouldGetCorrectPage() throws ServletException, IOException {
        signIn.doGet(request, response);
        verify(request).getRequestDispatcher("WEB-INF/sign-in.jsp");
    }

    @Test
    void doPostWithCorrectData_shouldSendRedirectToProfile() throws IOException {
        when(request.getParameter(Key.LOGIN)).thenReturn(CORRECT_LOGIN);
        when(request.getParameter(Key.PASSWORD)).thenReturn(CORRECT_PASSWORD);

        signIn.doPost(request, response);
        verify(request).getSession();
        verify(response).sendRedirect(Key.PROFILE);
    }

    @Test
    void doPostWithCorrectData_shouldSetAttributeUser() throws IOException {
        when(request.getParameter(Key.LOGIN)).thenReturn(CORRECT_LOGIN);
        when(request.getParameter(Key.PASSWORD)).thenReturn(CORRECT_PASSWORD);

        signIn.doPost(request, response);
        verify(request).getSession();
        verify(session).setAttribute(eq(Key.USER), any(UserTo.class));
    }

    @Test
    void doPostWithIncorrectData_shouldSendRedirectToSignIn() throws IOException {
        when(request.getParameter(Key.LOGIN)).thenReturn(testLogin);
        when(request.getParameter(Key.PASSWORD)).thenReturn(testPassword);

        signIn.doPost(request, response);
        verify(request).getSession();
        verify(response).sendRedirect(Key.SIGN_IN);
    }

    @Test
    void doPostWithIncorrectData_shouldSetAttributeError() throws IOException {
        when(request.getParameter(Key.LOGIN)).thenReturn(testLogin);
        when(request.getParameter(Key.PASSWORD)).thenReturn(testPassword);

        signIn.doPost(request, response);
        verify(request).getSession();
        verify(session).setAttribute(Key.ERROR_MESSAGE, Key.INVALID_USER_DATA);
    }

}