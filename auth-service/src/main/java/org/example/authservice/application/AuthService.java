package org.example.authservice.application;

import org.example.authservice.dto.request.LoginRequestDto;
import org.example.authservice.dto.request.MemberRegistRequestDto;
import org.example.authservice.dto.response.TokenResponse;

public interface AuthService {

    void registerMember(MemberRegistRequestDto registRequsetDto);

    TokenResponse login(LoginRequestDto requestDto);
}
