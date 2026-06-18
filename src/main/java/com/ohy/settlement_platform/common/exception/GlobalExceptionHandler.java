package com.ohy.settlement_platform.common.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import com.ohy.settlement_platform.common.ApiResponse;

@RestController
public class GlobalExceptionHandler {


    /**
     * CustomException 발생 시 처리하는 예외 핸들러
     *
     * - 비즈니스 로직에서 의도적으로 발생시킨 예외를 처리한다.
     * - HTTP 상태 코드는 400(Bad Request)로 응답한다.
     * - 예외에 포함된 메시지를 클라이언트에게 전달한다.
     *
     * @param e 발생한 CustomException
     * @return 실패 응답(ApiResponse)과 HTTP 400 상태 코드
     */
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ApiResponse<?>> handleCustomException(CustomException e) {
        return ResponseEntity
                .badRequest()
                .body(ApiResponse.fail(e.getMessage()));
    }

        /**
     * 처리되지 않은 모든 예외를 처리하는 전역 예외 핸들러
     *
     * - CustomException을 제외한 모든 Exception을 처리한다.
     * - 예상하지 못한 서버 내부 오류 발생 시 호출된다.
     * - 내부 오류 정보는 노출하지 않고 공통 메시지를 반환한다.
     * - HTTP 상태 코드는 500(Internal Server Error)로 응답한다.
     *
     * @param e 발생한 예외 객체
     * @return 실패 응답(ApiResponse)과 HTTP 500 상태 코드
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<?>> handleException(Exception e) {
        return ResponseEntity
                .internalServerError()
                .body(ApiResponse.fail("서버 오류가 발생했습니다."));
    }
}
