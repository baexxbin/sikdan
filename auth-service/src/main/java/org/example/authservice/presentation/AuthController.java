package org.example.authservice.presentation;


import lombok.RequiredArgsConstructor;
import org.example.authservice.application.AuthService;
import org.example.authservice.dto.request.LoginRequestDto;
import org.example.authservice.dto.request.MemberRegistRequestDto;
import org.example.authservice.dto.response.TokenResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    // 회원 가입 (member-service 호출)
    @PostMapping("/signup")
    public ResponseEntity<Void> signup(@RequestBody MemberRegistRequestDto requestDto) {
        authService.registerMember(requestDto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody LoginRequestDto request) {
        TokenResponse tokenResponse = authService.login(request);
        return ResponseEntity.ok(tokenResponse);
    }
}
