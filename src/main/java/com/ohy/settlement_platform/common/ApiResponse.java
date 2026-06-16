package com.ohy.settlement_platform.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor

/**
 * API 공통 응답 래퍼 클래스
 *
 * - 모든 API 응답을 동일한 구조로 통일하기 위한 DTO
 * - 성공/실패 여부, 메시지, 응답 데이터를 포함한다
 */
public class ApiResponse<T> {
    private boolean success;
    private String message;
    private T data;

    /**성공 - 기본형 */
    public static <T> ApiResponse<T> ok(T data){
        return new ApiResponse<>(true, "success", data);
    }
    /**성공 - 메시지형 */
    public static <T> ApiResponse<T> ok(String messgae, T data){
        return new ApiResponse<>(true, messgae, data);
    }

    /**실패 */
    public static <T> ApiResponse<T> fail(String message) {
        return new ApiResponse<>(true, message, null);
    }
}
