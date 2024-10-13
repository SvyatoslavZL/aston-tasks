package org.aston.application.servlets;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.aston.application.config.Configuration;
import org.aston.application.util.NanoSpring;

import java.io.IOException;

@WebServlet("")
public class HomeServlet extends HttpServlet {

    @Override
    public void init(ServletConfig config) {
        NanoSpring.find(Configuration.class).fillInDatabase();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("WEB-INF/home.jsp").forward(req, resp);
    }

}
