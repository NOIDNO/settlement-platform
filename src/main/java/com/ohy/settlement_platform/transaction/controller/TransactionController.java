package com.ohy.settlement_platform.transaction.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ohy.settlement_platform.common.ApiResponse;
import com.ohy.settlement_platform.transaction.dto.TransactionCreateRequest;
import com.ohy.settlement_platform.transaction.dto.TransactionResponse;
import com.ohy.settlement_platform.transaction.service.TransactionService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.security.Principal;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;



@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    /**
     * 거래 데이터 수신   
     * @param request
     * @return
     */
    @PostMapping
    public ResponseEntity<ApiResponse<?>> receiveTransaction(@Valid @RequestBody TransactionCreateRequest request) {
        transactionService.receiveTransaction(request);
        return ResponseEntity.ok(ApiResponse.ok("거래가 정상 수신되었습니다.", null));
    }

    /**
     * 내 거래 내역 조회
     * @param principal
     * @return
     */
    @GetMapping
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<ApiResponse<List<TransactionResponse>>> getMyTransactions (Principal principal) {
        List<TransactionResponse> transactions = transactionService.getMyTransactions(principal.getName());

        return ResponseEntity.ok(ApiResponse.ok(transactions));
    }
    
    
}
