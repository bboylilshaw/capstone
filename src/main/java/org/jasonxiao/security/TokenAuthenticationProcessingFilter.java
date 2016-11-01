package org.jasonxiao.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.jasonxiao.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Jason Xiao
 */
public class TokenAuthenticationProcessingFilter extends AbstractAuthenticationProcessingFilter {

    private static final Logger logger = LoggerFactory.getLogger(TokenAuthenticationProcessingFilter.class);
    private final TokenAuthenticationService tokenAuthenticationService;

    public TokenAuthenticationProcessingFilter(String defaultFilterProcessesUrl,
                                               TokenAuthenticationService tokenAuthenticationService) {
        super(new AntPathRequestMatcher(defaultFilterProcessesUrl));
        this.tokenAuthenticationService = tokenAuthenticationService;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        logger.info("Attempt to authenticate user");
        // if there is x-auth-token in header, use it to authenticate
        if (request.getHeader("x-auth-token") != null) {
            return tokenAuthenticationService.getAuthentication(request);
        }

        ObjectMapper objectMapper = new ObjectMapper();
        User user = objectMapper.readValue(request.getInputStream(), User.class);
        if (user != null) {
            return tokenAuthenticationService.getAuthentication(user);
        }
        return null;
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        logger.info("Set response header");
        User user = (User) (authResult.getPrincipal());
        tokenAuthenticationService.setResponseHeader(response, user);
        response.getWriter().write("login success");
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        logger.error("Login failed", failed);
        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.getWriter().write("login failed");
    }
}
