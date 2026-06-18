package com.ohy.settlement_platform.common.exception;

import lombok.Getter;
/**
 * 애플리케이션에서 사용하는 사용자 정의 예외 클래스
 *
 * - RuntimeException을 상속하여 언체크 예외(Unchecked Exception)로 사용한다.
 * - ErrorCode를 포함하여 예외 코드와 메시지를 함께 관리한다.
 * - 예외 발생 시 ErrorCode에 정의된 메시지를 부모 Exception에 전달한다.
 */
@Getter
public class CustomException extends RuntimeException {
    private final ErrorCode errorCode;

    public CustomException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
