package com.ohy.settlement_platform.transaction.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TransactionCreateRequest {
    @NotBlank(message = "API Key를 입력해주세요.")
    private String apiKey;

    @NotBlank(message = "거래 ID를 입력해주세요.")
    private String externalTxId;

    @NotNull(message = "거래 금액을 입력해주세요.")
    private Long amount;

    @NotNull(message = "거래 시각을 입력해주세요.")
    private LocalDateTime transactedAt;
}
