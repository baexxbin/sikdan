package org.example.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    // 식단
    MEAL_NOT_FOUND(HttpStatus.NOT_FOUND, "식단을 찾을 수 없습니다."),
    MEAL_NOT_MODIFIED(HttpStatus.NOT_MODIFIED, "식단 수정에 실패했습니다."),
    MEAL_NOT_DELETED(HttpStatus.NOT_FOUND, "식단 삭제에 실패했습니다."),

    UNAUTHORIZED_ACCESS(HttpStatus.FORBIDDEN, "접근 권한이 없습니다.");

    private final HttpStatus status;
    private final String message;

    ErrorCode(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

}
