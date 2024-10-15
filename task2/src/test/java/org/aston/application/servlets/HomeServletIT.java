package org.aston.application.servlets;

import jakarta.servlet.ServletException;
import org.aston.application.BaseIT;
import org.aston.application.util.NanoSpring;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.mockito.Mockito.verify;

class HomeServletIT extends BaseIT {

    private final HomeServlet home = NanoSpring.find(HomeServlet.class);

    @Test
    void doGet_shouldGetCorrectPage() throws ServletException, IOException {
        home.doGet(request, response);
        verify(request).getRequestDispatcher("WEB-INF/home.jsp");
    }

}