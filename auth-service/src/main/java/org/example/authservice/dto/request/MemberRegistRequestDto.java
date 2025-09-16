package org.example.authservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

// 회원가입 요청 DTO (프론트 -> auth)

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberRegistRequestDto {
    private String memberName;
    private String email;
    private String password;
    private String nickname;
}

