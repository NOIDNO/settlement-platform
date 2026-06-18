package com.ohy.settlement_platform.auth.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ReissueResponse {

    @NotBlank(message = "Refresh token을 입력해주세요")
    private String accessToken;
}
