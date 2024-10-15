package org.aston.application.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.aston.application.util.Key;

import java.io.IOException;

@WebFilter("/*")
public class ErrorCleanerFilter extends HttpFilter {
    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        super.doFilter(req, res, chain);

        if (req.getMethod().equalsIgnoreCase(Key.GET)) {
            HttpSession session = req.getSession(false);
            if (session != null) {
                session.removeAttribute(Key.ERROR_MESSAGE);
            }
        }
    }
}
