package com.ohy.settlement_platform.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 애플리케이션에서 사용하는 에러 코드 정의 Enum
 *
 * - 예외 상황을 문자열이 아닌 타입 안전한 값으로 관리
 * - 각 에러 코드마다 사용자에게 보여줄 메시지를 포함
 * - CustomException과 함께 사용되어 예외 표준화를 담당
 */
@Getter
@AllArgsConstructor
public enum ErrorCode {
    MEMBER_NOT_FOUND("회원을 찾을 수 없습니다."),
    DUPLICATE_EMAIL("이미 사용 중인 이메일입니다."),
    INVALID_PASSWORD("비밀번호가 올바르지 않습니다."),
    CHANNEL_NOT_FOUND("채널을 찾을 수 없습니다."),
    INVALID_API_KEY("유효하지 않은 API 키입니다."),
    DUPLICATE_TRANSACTION("이미 처리된 거래입니다."),
    SETTLEMENT_NOT_FOUND("정산 내역을 찾을 수 없습니다."),
    UNAUTHORIZED("접근 권한이 없습니다.");

    private final String message;

}
