package org.example.sikdan.domain.member.model.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Member {
    private Long memberId;
    private String memberName;
    private String email;
    private String password;
    private String nickname;
    private LocalDateTime createdAt;
}
