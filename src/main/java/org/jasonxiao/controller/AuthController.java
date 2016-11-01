package org.jasonxiao.controller;

import org.jasonxiao.security.UserAuthentication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Jason Xiao
 */
@RestController
public class AuthController {

    @GetMapping("/api/current")
    public ResponseEntity getCurrentUser(Authentication authentication) {
        if (authentication instanceof UserAuthentication) {
            return ResponseEntity.ok(authentication.getPrincipal());
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }
}
