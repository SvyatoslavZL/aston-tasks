package org.aston.application.servlets;

import jakarta.servlet.ServletException;
import org.aston.application.BaseIT;
import org.aston.application.dto.Role;
import org.aston.application.util.Key;
import org.aston.application.util.NanoSpring;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Collection;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;

class UserListServletIT extends BaseIT {

    private final UserListServlet userList = NanoSpring.find(UserListServlet.class);

    @Test
    void init_shouldSetAttributeRoles() {
        userList.init(servletConfig);
        verify(servletContext).setAttribute(Key.ROLES, Role.values());
    }

    @Test
    void doGet_shouldGetCorrectPage() throws ServletException, IOException {
        userList.doGet(request, response);
        verify(request).setAttribute(eq(Key.USERS), any(Collection.class));
        verify(request).getRequestDispatcher("WEB-INF/user-list.jsp");
    }

}