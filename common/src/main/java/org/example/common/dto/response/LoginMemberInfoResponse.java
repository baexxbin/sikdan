package org.example.common.dto.response;

// 로그인된 사용자의 보여질 정보
public record LoginMemberInfoResponse(
        String nickname
        // 향후 더 보여질 내용 추가 (ex. 소속 등등)
) {
}
