package org.example.authservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberRegistRequestDto {
    private String memberName;
    private String email;
    private String password;
    private String nickname;
}

