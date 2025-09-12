package org.example.memberservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.memberservice.model.vo.Member;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberCreateRequest {
    private String memberName;
    private String email;
    private String password;
    private String nickname;

    public Member toEntity(PasswordEncoder passwordEncoder) {
        return Member.builder()
                .memberName(memberName)
                .email(email)
                .password(passwordEncoder.encode(password))
                .nickname(nickname)
                .build();
    }
}
