package org.example.authservice.application.impl;

import lombok.RequiredArgsConstructor;
import org.example.authservice.application.AuthService;
import org.example.authservice.dto.client.MemberCreateRequest;
import org.example.authservice.dto.request.LoginRequestDto;
import org.example.authservice.dto.request.MemberRegistRequestDto;
import org.example.authservice.dto.response.TokenResponse;
import org.example.authservice.infrastructure.client.MemberClient;
import org.example.authservice.jwt.JwtTokenProvider;
import org.example.common.dto.request.MemberInfoDto;
import org.example.common.dto.response.MemberResponse;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final MemberClient memberClient;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void registerMember(MemberRegistRequestDto registryRequestDto) {
        // member-service에 전달할 DTO 변환
        MemberCreateRequest createRequest = new MemberCreateRequest(
                registryRequestDto.getMemberName(),
                registryRequestDto.getEmail(),
                passwordEncoder.encode(registryRequestDto.getPassword()), // 비밀번호 해싱
                registryRequestDto.getNickname()
        );

        memberClient.registerMember(createRequest);

    }

    @Override
    public TokenResponse login(LoginRequestDto requestDto) {
        // member-service 호출
        MemberResponse member = memberClient.findByEmail(requestDto.email());

        // 비밀번호 검증
        if (!passwordEncoder.matches(requestDto.password(), member.password())) {
            throw new IllegalArgumentException("이메일 또는 비밀번호가 올바르지 않습니다.");
        }

        // TODO: 일단 단순 이메일 조건으로 분기해놓음
        // 권한 결정
        List<String> roles = member.email().equals("admin@example.com")
                ? List.of("ROLE_ADMIN")
                : List.of("ROLE_USER");

        MemberInfoDto memberInfo = new MemberInfoDto(
                member.memberId(),
                member.email(),
                roles
        );

        String token = jwtTokenProvider.generateToken(memberInfo);

        return new TokenResponse(token);
    }
}
