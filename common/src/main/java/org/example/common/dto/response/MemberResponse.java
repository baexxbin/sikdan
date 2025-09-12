package org.example.common.dto.response;

import java.util.List;

// 서비스 간 통신용 공통 DTO
public record MemberResponse(
        Long memberId,
        String memberName,
        String email,
        String password,
        List<String> roles
) {
}
