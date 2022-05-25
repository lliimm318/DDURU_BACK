package com.huitdduru.madduru.exception;

import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ExceptionHandlingFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (BaseException e) {
            responseError(response, new ErrorResponse(e.getErrorCode().getStatus(), e.getMessage()));
        } catch (Exception e) {
            responseError(response, new ErrorResponse(500, null));
        }
    }

    private void responseError(HttpServletResponse response, ErrorResponse errorResponse) throws IOException {
        response.setStatus(errorResponse.getStatus());
        response.setContentType("application/json");
        response.getWriter().write(errorResponse.toString());
    }

}
