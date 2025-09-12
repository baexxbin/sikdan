package org.example.authservice.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.example.authservice.config.JwtProperties;
import org.example.common.dto.request.MemberInfoDto;
import org.example.authservice.application.impl.CustomUserDetails;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import org.springframework.stereotype.Component;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
@Getter
public class JwtTokenProvider {

    private final JwtProperties jwtProperties;
//    private final UserDetailsService userDetailsService;
    private Key key;

    public JwtTokenProvider(
            JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
    }

    @PostConstruct      // 의존성 주입이 모두 끝난 뒤 자동으로 실행되는 초기화 메서드
    protected void init() {
        /*
        * jwtProperties.getSecret() : application.yml에서 가죠운 Base64 인코딩된 문자열
        * JwtTokenProvider.key : 실제 JWT 서명에 사용하는 java.security.Key 객체로 변환한 결과
        */
        byte[] keyBytes = Base64.getDecoder().decode(jwtProperties.getSecret());
        this.key = Keys.hmacShaKeyFor(keyBytes);
//        this.key = Keys.hmacShaKeyFor(jwtProperties.getSecret().getBytes(StandardCharsets.UTF_8));
    }

    // 토큰 생성
    public String generateToken(MemberInfoDto memberInfo) {      // 이때 roles는 security에서 인가 용도로 사용되는 권한 정보 (ROLE_USER, ROLE_ADMIN ..)
        Claims claims = Jwts.claims().setSubject(memberInfo.getEmail());        // payload의 sub(토큰 주체)에 들어갈 값, (표준 Claim)
        claims.put("memberId", memberInfo.getEmail());                        // (사용자 정의 Claim)
        claims.put("roles", memberInfo.getRoles());
        claims.put("email", memberInfo.getEmail());

        Date now = new Date();
        Date validity = new Date(now.getTime() + jwtProperties.getExpiration());

        log.info("유효시간 설정(ms): {}", jwtProperties.getExpiration());
        log.info("유효시간 validity: {}", validity);


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
            log.warn("JWT 검증 실패: {}", e.getMessage());
            return false;
        }
    }

    // 토큰에서 인증 정보 추출
    public Authentication getAuthentication(String token) {
        Claims claims = getClaims(token);

        // 토큰에서 필요한 값 꺼내기
        Long memberId = ((Number) claims.get("memberId")).longValue();
        String email = claims.get("email", String.class);
        List<String> roles = claims.get("roles", List.class);


        List<GrantedAuthority> authorities = roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        CustomUserDetails customUserDetails =
                new CustomUserDetails(memberId, email, authorities);

        return new UsernamePasswordAuthenticationToken(
                customUserDetails,
                null,
                authorities
        );
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
