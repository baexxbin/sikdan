package org.example.memberservice.dto.response;

import java.util.List;

public record MemberResponseDto(Long memberId,
                                String memberName,
                                String email,
                                List<String> roles) {
}
