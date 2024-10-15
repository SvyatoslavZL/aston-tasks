package org.aston.application.filter;

import jakarta.servlet.ServletException;
import org.aston.application.BaseIT;
import org.aston.application.util.Key;
import org.aston.application.util.NanoSpring;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ErrorCleanerFilterIT extends BaseIT {

    private final ErrorCleanerFilter filter = NanoSpring.find(ErrorCleanerFilter.class);

    @Test
    void doFilter_shouldRemoveAttributeIfNeeded() throws ServletException, IOException {
        when(request.getMethod()).thenReturn(Key.GET);
        when(request.getSession(false)).thenReturn(session);

        filter.doFilter(request, response, chain);

        verify(request).getMethod();
        verify(request).getSession(false);
        verify(session).removeAttribute(Key.ERROR_MESSAGE);
    }

}