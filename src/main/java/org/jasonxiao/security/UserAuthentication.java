package org.jasonxiao.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * @author Jason Xiao
 */
public class UserAuthentication implements Authentication {

    private UsernamePasswordAuthenticationToken token;

    public UserAuthentication(UsernamePasswordAuthenticationToken token) {
        this.token = token;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return token.getAuthorities();
    }

    @Override
    public Object getCredentials() {
        return token.getCredentials();
    }

    @Override
    public Object getDetails() {
        return token.getDetails();
    }

    @Override
    public Object getPrincipal() {
        return token.getPrincipal();
    }

    @Override
    public boolean isAuthenticated() {
        return token.isAuthenticated();
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        token.setAuthenticated(isAuthenticated);
    }

    @Override
    public String getName() {
        return token.getName();
    }

}
