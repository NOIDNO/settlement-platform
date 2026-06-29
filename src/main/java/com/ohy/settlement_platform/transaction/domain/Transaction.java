package com.ohy.settlement_platform.transaction.domain;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {
    private Long transactionId;
    private Long channelId;
    private String externalTxId;   // 채널측 거래 ID (중복 방지용)
    private Long amount;
    private String status;          // APPROVED, CANCELLED
    private LocalDateTime transactedAt; // 실제 거래 발생 시각
    private LocalDateTime createdAt;
}
