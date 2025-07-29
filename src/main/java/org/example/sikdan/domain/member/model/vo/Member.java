package org.example.sikdan.domain.member.model.vo;

import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Member {
    private Long memberId;
    private String memberName;
    private String email;
    private String password;
    private String nickname;
    private LocalDateTime createdAt;
}
