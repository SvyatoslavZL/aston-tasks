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
import java.util.Optional;

@WebServlet("/edit-user")
public class EditUserServlet extends HttpServlet {

    private final UserService userService = NanoSpring.find(UserService.class);

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String stringId = req.getParameter(Key.ID);
        if (stringId != null) {
            long id = Long.parseLong(stringId);
            Optional<UserTo> optionalUser = userService.get(id);
            if (optionalUser.isPresent()) {
                UserTo userTo = optionalUser.get();
                req.setAttribute(Key.USER, userTo);
            }
        }
        req.getRequestDispatcher("WEB-INF/edit-user.jsp").forward(req, resp);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        UserTo userTo = new UserTo(
                RequestHelper.getId(req),
                req.getParameter(Key.LOGIN),
                req.getParameter(Key.PASSWORD),
                Role.valueOf(req.getParameter(Key.ROLE))
        );

        String action = req.getParameter(Key.ACTION);
        if ((Key.DELETE).equals(action)) {
            userService.delete(userTo);
        } else if ((Key.UPDATE).equals(action)) {
            userService.update(userTo);
        }

        resp.sendRedirect(Key.USER_LIST);
    }


}
