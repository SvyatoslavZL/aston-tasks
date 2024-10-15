package org.aston.application.servlets;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.aston.application.dto.Role;
import org.aston.application.dto.UserTo;
import org.aston.application.util.Key;

import java.io.IOException;

@WebServlet("/profile")
public class ProfileServlet extends HttpServlet {

    @Override
    public void init(ServletConfig config) {
        config.getServletContext().setAttribute(Key.ROLES, Role.values());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("WEB-INF/profile.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if (req.getParameter(Key.LOGOUT) == null) {
            HttpSession session = req.getSession();
            UserTo userTo = (UserTo) session.getAttribute(Key.USER);
            resp.sendRedirect(Key.EDIT_USER + "?id=" + userTo.getId());
        } else {
            resp.sendRedirect(Key.LOGOUT);
        }
    }
}
