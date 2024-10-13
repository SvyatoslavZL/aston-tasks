package org.aston.application.util;

import jakarta.servlet.http.HttpServletRequest;

public class RequestHelper {

    private RequestHelper() {
    }

    public static Long getId(HttpServletRequest req) {
        return getId(req, Key.ID);
    }

    public static Long getId(HttpServletRequest req, String key) {
        String id = req.getParameter(key);
        return id != null && !id.isBlank()
                ? Long.parseLong(id)
                : 0L;
    }

    public static void setError(HttpServletRequest req, String errorMessage) {
        req.getSession().setAttribute(Key.ERROR_MESSAGE, errorMessage);
    }
}
