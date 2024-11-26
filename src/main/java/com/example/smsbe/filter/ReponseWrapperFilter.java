package com.example.smsbe.filter;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.example.smsbe.response.ResponseWrapper;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;

@Component
public class ReponseWrapperFilter implements Filter {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        if (request instanceof HttpServletRequest httpRequest && response instanceof HttpServletResponse httpResponse) {
            ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(httpResponse);
            chain.doFilter(httpRequest, responseWrapper);
            byte[] responseBody = responseWrapper.getContentAsByteArray();
            String responseBodyString = new String(responseBody);
            ResponseWrapper<?> responseWrapperObj = objectMapper.readValue(responseBodyString, ResponseWrapper.class);
            httpResponse.setStatus(responseWrapperObj.getStatusCode());
            responseWrapper.copyBodyToResponse();
        } else {
            chain.doFilter(request, response);
        }
    }
}
