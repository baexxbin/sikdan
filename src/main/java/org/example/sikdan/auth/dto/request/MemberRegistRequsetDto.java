package org.example.sikdan.auth.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.sikdan.domain.member.model.vo.Member;
import org.springframework.security.crypto.password.PasswordEncoder;

@NoArgsConstructor      // json 역직렬화용
@AllArgsConstructor
@Getter
public class MemberRegistRequsetDto {

    private String memberName;
    private String email;
    private String password;
    private String nickname;

    // 엔티티 변환 (비밀번호 암호화 포함)
    public Member toEntity(PasswordEncoder passwordEncoder) {
        return new Member(
                null,       // memberId DB에서 자동 생성
                memberName,
                email,
                passwordEncoder.encode(password),
                nickname,
                null        // createdAt DB default 값 사용
        );
    }
}
