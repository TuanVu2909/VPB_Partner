//package com.lendbiz.p2p.api.configs;
//
//import org.springframework.security.web.AuthenticationEntryPoint;
//
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
//public final class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {
//    @Override
//    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, org.springframework.security.core.AuthenticationException e) throws IOException, ServletException {
//        httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//        httpServletResponse.getWriter().write("Unauthorized");
//    }
//}