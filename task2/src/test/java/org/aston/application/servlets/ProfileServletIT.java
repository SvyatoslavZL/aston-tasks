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

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ProfileServletIT extends BaseIT {

    private final ProfileServlet profile = NanoSpring.find(ProfileServlet.class);
    private final UserService userService = NanoSpring.find(UserService.class);

    @Test
    void init_shouldSetAttributeRoles() {
        profile.init(servletConfig);
        verify(servletContext).setAttribute(Key.ROLES, Role.values());
    }

    @Test
    void doGet_shouldGetCorrectPage() throws ServletException, IOException {
        profile.doGet(request, response);
        verify(request).getRequestDispatcher("WEB-INF/profile.jsp");
    }

    @Test
    void doPostLogout_shouldSendRedirectToLogout() throws IOException {
        when(request.getParameter(Key.LOGOUT)).thenReturn("");

        profile.doPost(request, response);
        verify(response).sendRedirect(Key.LOGOUT);
    }

    @Test
    void doPostEdit_shouldSendRedirectToEditUserWithCorrectId() throws IOException {
        when(request.getParameter(Key.LOGOUT)).thenReturn(null);
        UserTo userTo = userService.getAll().stream().findFirst().orElseThrow();
        when(session.getAttribute(Key.USER)).thenReturn(userTo);

        profile.doPost(request, response);
        verify(request).getSession();
        verify(response).sendRedirect(Key.EDIT_USER + "?id=" + userTo.getId());
    }

}