package com.example.testingspringbootapplications;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.UUID;

/**
 * Filter to add a unique Correlation ID (trace-id) to every request's logging context.
 */
@Component
public class MdcFilter implements Filter {

    private static final String CORRELATION_ID_HEADER = "X-Correlation-ID";
    private static final String CORRELATION_ID_LOG_VAR = "correlationId";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String correlationId = httpRequest.getHeader(CORRELATION_ID_HEADER);

        if (correlationId == null || correlationId.isEmpty()) {
            correlationId = UUID.randomUUID().toString();
        }

        // Add to MDC - this will be automatically appended to every log line
        MDC.put(CORRELATION_ID_LOG_VAR, correlationId);

        try {
            chain.doFilter(request, response);
        } finally {
            // CRITICAL: Always clear MDC for the thread!
            MDC.remove(CORRELATION_ID_LOG_VAR);
        }
    }
}
