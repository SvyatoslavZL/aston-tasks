package org.aston.application.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.aston.application.dto.UserTo;
import org.aston.application.service.UserService;
import org.aston.application.util.Key;
import org.aston.application.util.NanoSpring;
import org.aston.application.util.RequestHelper;

import java.io.IOException;
import java.util.Optional;

@WebServlet("/sign-in")
public class SignInServlet extends HttpServlet {

    private final UserService userService = NanoSpring.find(UserService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("WEB-INF/sign-in.jsp").forward(req, resp);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String login = req.getParameter(Key.LOGIN);
        String password = req.getParameter(Key.PASSWORD);
        Optional<UserTo> user = userService.find(login, password);
        if (user.isPresent()) {
            req.getSession().setAttribute(Key.USER, user.get());
            resp.sendRedirect("profile");
        } else {
            RequestHelper.setError(req, Key.INVALID_USER_DATA);
            resp.sendRedirect("sign-in");
        }
    }

}
