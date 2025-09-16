package org.example.memberservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.memberservice.model.vo.Member;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberCreateRequest {
    private String memberName;
    private String email;
    private String password;
    private String nickname;

    public Member toEntity() {
        return Member.builder()
                .memberName(memberName)
                .email(email)
                .password(password)     // 이미 auth-service에서 해싱되어 들어옴
                .nickname(nickname)
                .role("ROLE_USER")
                .build();
    }
}
