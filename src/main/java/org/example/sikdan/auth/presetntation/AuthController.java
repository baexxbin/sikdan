package org.example.sikdan.auth.presetntation;


import lombok.RequiredArgsConstructor;
import org.example.sikdan.auth.application.AuthService;
import org.example.sikdan.auth.dto.request.LoginRequestDto;
import org.example.sikdan.auth.dto.request.MemberRegistRequsetDto;
import org.example.sikdan.auth.dto.response.TokenResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<Void> signup(@RequestBody MemberRegistRequsetDto requestDto) {
        authService.registerMember(requestDto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody LoginRequestDto request) {
        TokenResponse tokenResponse = authService.login(request);
        return ResponseEntity.ok(tokenResponse);
    }
}
