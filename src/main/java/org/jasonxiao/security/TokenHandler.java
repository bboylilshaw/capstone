package org.jasonxiao.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.jasonxiao.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

/**
 * @author Jason Xiao
 */
@Component
public class TokenHandler {

    private final String secret;
    private final UserDetailsService userDetailsService;

    @Autowired
    public TokenHandler(@Value("${token.secret}") String secret,
                        UserDetailsService userDetailsService) {
        this.secret = secret;
        this.userDetailsService = userDetailsService;
    }

    public UserDetails parseUserFromToken(String token) {
        String username = Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
        return userDetailsService.loadUserByUsername(username);
    }

    public String createTokenForUser(UserDetails user) {
        return Jwts.builder()
                .setSubject(user.getUsername())
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

}