package org.example.commonsecurity.auth;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.Authentication;

public interface CustomUserDetailsFactory {
    Authentication createAuthentication(Claims claims);
}
