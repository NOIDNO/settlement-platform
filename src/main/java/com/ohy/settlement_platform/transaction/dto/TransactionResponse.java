package com.ohy.settlement_platform.transaction.dto;

import java.time.LocalDateTime;

import com.ohy.settlement_platform.transaction.domain.Transaction;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TransactionResponse {
    private Long transactionId;
    private String externalTxId;
    private Long amount;
    private String status;
    private LocalDateTime transactedAt;
    private LocalDateTime createdAt;

    public static TransactionResponse from(Transaction tx) {
        return new TransactionResponse(
                tx.getTransactionId(),
                tx.getExternalTxId(),
                tx.getAmount(),
                tx.getStatus(),
                tx.getTransactedAt(),
                tx.getCreatedAt()
        );
    }
}
