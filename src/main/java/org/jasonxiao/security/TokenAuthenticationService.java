package org.jasonxiao.security;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Jason Xiao
 */
public class TokenAuthenticationService {

    private static final String AUTH_HEADER_NAME = "X-Auth-Token";
    private final TokenHandler tokenHandler;
    private final AuthenticationManager authenticationManager;

    public TokenAuthenticationService(TokenHandler tokenHandler, AuthenticationManager authenticationManager) {
        this.tokenHandler = tokenHandler;
        this.authenticationManager = authenticationManager;
    }

    public Authentication getAuthentication(HttpServletRequest request) {
        final String token = request.getHeader(AUTH_HEADER_NAME);
        if (token != null) {
            final UserDetails user = tokenHandler.parseUserFromToken(token);
            if (user != null) {
                return this.getAuthentication(user);
            }
        }
        return null;
    }

    public Authentication getAuthentication(UserDetails user) {
        Assert.notNull(user);
        UsernamePasswordAuthenticationToken loginToken = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
        Authentication authentication = authenticationManager.authenticate(loginToken);
        if (authentication != null && authentication instanceof UsernamePasswordAuthenticationToken) {
            return new UserAuthentication((UsernamePasswordAuthenticationToken)authentication);
        }
        return null;
    }

    public void setResponseHeader(HttpServletResponse response, UserDetails user) {
        String token = tokenHandler.createTokenForUser(user);
        response.addHeader(AUTH_HEADER_NAME, token);
    }
}
