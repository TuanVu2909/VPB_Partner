package com.lendbiz.p2p.api.configs;

import com.lendbiz.p2p.api.model.MyUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtFilter extends OncePerRequestFilter {
    @Autowired
    JwtProvider provider;
    @Autowired
    MyUserDetailService myUserDetailService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = getJwt(request);
        if (token != null && provider.validateToken(token)) {
            String username = provider.getUserNameByToken(token);
            UserDetails userDetails = myUserDetailService.loadUserByUsername(username);
            UsernamePasswordAuthenticationToken tokenAuth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            tokenAuth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(tokenAuth);
        }
        filterChain.doFilter(request,response);
    }


    private String getJwt(HttpServletRequest request) {
        String tokenParam = request.getParameter("token");
        if (tokenParam!= null){
            return tokenParam;
        }
        String athHeader = request.getHeader("Authorization");
        if (athHeader != null && athHeader.startsWith("Bearer")) {
            return athHeader.replace("Bearer", "");
        }
        return null;
    }
}
