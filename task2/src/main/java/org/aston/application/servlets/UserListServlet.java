package org.aston.application.servlets;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.aston.application.util.NanoSpring;
import org.aston.application.dto.Role;
import org.aston.application.dto.UserTo;
import org.aston.application.service.UserService;
import org.aston.application.util.Key;

import java.io.IOException;
import java.util.Collection;

@WebServlet("/user-list")
public class UserListServlet extends HttpServlet {

    private final UserService userService = NanoSpring.find(UserService.class);

    @Override
    public void init(ServletConfig config) {
        config.getServletContext().setAttribute(Key.ROLES, Role.values());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Collection<UserTo> users = userService.getAll();
        req.setAttribute(Key.USERS, users);
        req.getRequestDispatcher("WEB-INF/user-list.jsp").forward(req, resp);
    }

}
