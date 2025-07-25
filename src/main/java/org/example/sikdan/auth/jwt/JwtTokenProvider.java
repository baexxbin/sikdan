package org.example.sikdan.auth.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import org.example.sikdan.config.JwtProperties;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Component
public class JwtTokenProvider {

    private final JwtProperties jwtProperties;
    private final UserDetailsService userDetailsService;
    private Key key;

    public JwtTokenProvider(
            JwtProperties jwtProperties,
            UserDetailsService userDetailsService) {
        this.jwtProperties = jwtProperties;
        this.userDetailsService = userDetailsService;
    }

    @PostConstruct      // 의존성 주입이 모두 끝난 뒤 자동으로 실행되는 초기화 메서드
    protected void init() {
        /*
        * jwtProperties.getSecret() : application.yml에서 가죠운 Base64 인코딩된 문자열
        * JwtTokenProvider.key : 실제 JWT 서명에 사용하는 java.security.Key 객체로 변환한 결과
        */
        byte[] keyBytes = Base64.getDecoder().decode(jwtProperties.getSecret());
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    // 토큰 생성
    public String generateToken(Long memberId, String email, List<String> roles) {      // 이때 roles는 security에서 인가 용도로 사용되는 권한 정보 (ROLE_USER, ROLE_ADMIN ..)
        Claims claims = Jwts.claims().setSubject(email);        // payload의 sub(토큰 주체)에 들어갈 값, (표준 Claim)
        claims.put("memberId", memberId);                        // (사용자 정의 Claim)
        claims.put("roles", roles);

        Date now = new Date();
        Date validity = new Date(now.getTime() + jwtProperties.getExpiration());

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    // 토큰에서 사용자 이메일, 권한 등 추출
    public Claims getClaims(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (JwtException | IllegalArgumentException e) {
            throw new RuntimeException("Invalid JWT token", e);
        }
    }


    // 토큰 유효성 검사
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);     // 여기서 Exception 발생 안 하면 유효
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    // 토큰에서 인증 정보 추출
    public Authentication getAuthentication(String token) {
        String username = getUsername(token);
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

    // 토큰에서 사용자명 추출
    public String getUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key) // Key 객체 사용
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }


    // 요청 헤더에서 토큰 추출
    public String resolveToken(HttpServletRequest req) {
        String bearerToken = req.getHeader("Authorization");

        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }

        return null;
    }
}
