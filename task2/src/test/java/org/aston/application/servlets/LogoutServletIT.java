package org.aston.application.servlets;

import jakarta.servlet.ServletException;
import org.aston.application.BaseIT;
import org.aston.application.util.NanoSpring;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.mockito.Mockito.verify;

class LogoutServletIT extends BaseIT {

    private final LogoutServlet logout = NanoSpring.find(LogoutServlet.class);

    @Test
    void doGet_shouldInvalidateSession() throws ServletException, IOException {
        logout.doGet(request, response);

        verify(request).getSession();
        verify(session).invalidate();
    }

    @Test
    void doGet_shouldGetCorrectPage() throws ServletException, IOException {
        logout.doGet(request, response);

        verify(request).getRequestDispatcher("WEB-INF/sign-in.jsp");
    }


}