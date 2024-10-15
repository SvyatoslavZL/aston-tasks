package org.aston.application;

import jakarta.servlet.FilterChain;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.aston.application.dto.Role;
import org.aston.application.dto.UserTo;
import org.mockito.Mockito;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BaseIT extends TestContainer {
    protected final HttpServletRequest request;
    protected final HttpServletResponse response;
    protected final FilterChain chain;
    protected final HttpSession session;
    protected final RequestDispatcher requestDispatcher;
    protected final ServletConfig servletConfig;
    protected final ServletContext servletContext;
    protected final UserTo testUserTo;
    protected final String testLogin;
    protected final String testPassword;

    protected BaseIT() {
        //servlet configurator
        servletConfig = mock(ServletConfig.class);
        servletContext = mock(ServletContext.class);
        when(servletConfig.getServletContext()).thenReturn(servletContext);
        //current op
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        chain = mock(FilterChain.class);
        session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);
        requestDispatcher = mock(RequestDispatcher.class);
        when(request.getRequestDispatcher(Mockito.anyString())).thenReturn(requestDispatcher);
        //test data
        testUserTo = new UserTo(1L, "testUser", "testUser", Role.USER);
        testLogin = "testLogin";
        testPassword = "testPassword";
    }
}
