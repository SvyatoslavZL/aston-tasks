package org.aston.application.servlets;

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
import org.aston.application.util.RequestHelper;

import java.io.IOException;

@WebServlet("/sign-up")
public class SignUpServlet extends HttpServlet {

    private final UserService userService = NanoSpring.find(UserService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("WEB-INF/sign-up.jsp").forward(req, resp);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        UserTo userTo = new UserTo(
                RequestHelper.getId(req),
                req.getParameter(Key.LOGIN),
                req.getParameter(Key.PASSWORD),
                Role.valueOf(req.getParameter(Key.ROLE))
        );
        userService.create(userTo);
        resp.sendRedirect(Key.USER_LIST);
    }

}
