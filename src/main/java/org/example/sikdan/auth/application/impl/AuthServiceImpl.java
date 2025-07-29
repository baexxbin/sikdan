package org.example.sikdan.auth.application.impl;

import lombok.RequiredArgsConstructor;
import org.example.sikdan.auth.application.AuthService;
import org.example.sikdan.auth.dto.request.LoginRequestDto;
import org.example.sikdan.auth.dto.request.MemberRegistRequsetDto;
import org.example.sikdan.auth.dto.response.TokenResponse;
import org.example.sikdan.auth.jwt.JwtTokenProvider;
import org.example.sikdan.domain.member.application.MemberService;
import org.example.sikdan.domain.member.model.vo.Member;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void registerMember(MemberRegistRequsetDto registryRequestDto) {
        Member member = registryRequestDto.toEntity(passwordEncoder);
        memberService.registerMember(member);

    }

    @Override
    public TokenResponse login(LoginRequestDto requestDto) {
        Member member = memberService.findByEmail(requestDto.email());
        if (member == null || !passwordEncoder.matches(requestDto.password(), member.getPassword())) {
            throw new IllegalArgumentException("이메일 또는 비밀번호가 올바르지 않습니다.");
        }


        // TODO: 일단 단순 이메일 조건으로 분기해놓음
        List<String> roles = member.getEmail().equals("admin@example.com") ?
                List.of("ROLE_ADMIN") :
                List.of("ROLE_USER");

        String token = jwtTokenProvider.generateToken( member.getMemberId(), member.getEmail(), roles);
        return new TokenResponse(token);
    }
}
