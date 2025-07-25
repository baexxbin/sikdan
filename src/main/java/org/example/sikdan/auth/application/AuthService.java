package org.example.sikdan.auth.application;

import org.example.sikdan.auth.dto.request.LoginRequestDto;
import org.example.sikdan.auth.dto.request.MemberRegistRequsetDto;
import org.example.sikdan.auth.dto.response.TokenResponse;

public interface AuthService {

    void registerMember(MemberRegistRequsetDto registRequsetDto);

    TokenResponse login(LoginRequestDto requestDto);
}
