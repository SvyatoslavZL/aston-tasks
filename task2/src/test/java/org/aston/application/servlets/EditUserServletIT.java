package org.aston.application.servlets;

import jakarta.servlet.ServletException;
import org.aston.application.BaseIT;
import org.aston.application.dto.Role;
import org.aston.application.dto.UserTo;
import org.aston.application.entity.User;
import org.aston.application.mapping.EntityMapper;
import org.aston.application.repository.UserRepository;
import org.aston.application.service.UserService;
import org.aston.application.util.Key;
import org.aston.application.util.NanoSpring;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class EditUserServletIT extends BaseIT {

    private final EditUserServlet editUser = NanoSpring.find(EditUserServlet.class);
    private final UserService userService = NanoSpring.find(UserService.class);

    @Test
    void doGet_shouldGetCorrectPage() throws ServletException, IOException {
        UserTo userTo = userService.getAll().stream().findFirst().orElseThrow();
        when(request.getParameter(Key.ID)).thenReturn(userTo.getId().toString());

        editUser.doGet(request, response);
        verify(request).getRequestDispatcher("WEB-INF/edit-user.jsp");
    }

    @Test
    void doGet_shouldSetAttributeUser() throws ServletException, IOException {
        UserTo userTo = userService.getAll().stream().findFirst().orElseThrow();
        when(request.getParameter(Key.ID)).thenReturn(userTo.getId().toString());

        editUser.doGet(request, response);
        verify(request).setAttribute(eq(Key.USER), any(UserTo.class));
    }

    @Test
    void doPost_shouldSendRedirectToUserList() throws IOException {
        when(request.getParameter(Key.LOGIN)).thenReturn(testLogin);
        when(request.getParameter(Key.PASSWORD)).thenReturn(testPassword);
        when(request.getParameter(Key.ROLE)).thenReturn(Role.USER.toString());

        editUser.doPost(request, response);
        verify(response).sendRedirect(Key.USER_LIST);
    }

    @Test
    void doPostUpdate_shouldInvokeUpdateMethod() {
        when(request.getParameter(Key.ACTION)).thenReturn(Key.UPDATE);

        String expected = Key.UPDATE;
        String actual = request.getParameter(Key.ACTION);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void doPostDelete_shouldInvokeDeleteMethod() {
        when(request.getParameter(Key.ACTION)).thenReturn(Key.DELETE);

        String expected = Key.DELETE;
        String actual = request.getParameter(Key.ACTION);
        Assertions.assertEquals(expected, actual);
    }

}