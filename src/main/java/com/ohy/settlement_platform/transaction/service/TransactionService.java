package com.ohy.settlement_platform.transaction.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.ohy.settlement_platform.channel.domain.Channel;
import com.ohy.settlement_platform.channel.mapper.ChannelMapper;
import com.ohy.settlement_platform.common.exception.CustomException;
import com.ohy.settlement_platform.common.exception.ErrorCode;
import com.ohy.settlement_platform.member.domain.Member;
import com.ohy.settlement_platform.member.mapper.MemberMapper;
import com.ohy.settlement_platform.transaction.domain.Transaction;
import com.ohy.settlement_platform.transaction.dto.TransactionCreateRequest;
import com.ohy.settlement_platform.transaction.dto.TransactionResponse;
import com.ohy.settlement_platform.transaction.mapper.TransactionMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionMapper transactionMapper;
    private final ChannelMapper channelMapper;
    private final MemberMapper memberMapper;

    /**
     * 거래데이터 수신 (외부데이터 - apikey로 인증)
     * @param request
     */
    public void receiveTransaction(TransactionCreateRequest request) {
        //apikey로 채널 확인
        Channel channel = channelMapper.findByApiKey(request.getApiKey())
                .orElseThrow(()-> new CustomException(ErrorCode.INVALID_API_KEY));

        //중복거래 체크
        boolean exists = transactionMapper.existsByChannelIdAndExternalTxId(channel.getChannelId(), request.getExternalTxId());
        
        if(exists) {
            throw new CustomException(ErrorCode.DUPLICATE_TRANSACTION);
        }

        //정상 거래 저장
        Transaction transaction = Transaction.builder()
                .channelId(channel.getChannelId())
                .externalTxId(request.getExternalTxId())
                .amount(request.getAmount())
                .status("APPROVED")
                .transactedAt(request.getTransactedAt())
                .build();

        transactionMapper.insertTransaction(transaction);

    }

    /**
     * 판매자 본인의 거래 내역 조회
     * @param email
     * @return TransactionResponse
     */
    public List<TransactionResponse> getMyTransactions(String email){
        Member member = memberMapper.findByEmail(email)
                .orElseThrow(()-> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

                return transactionMapper.findBySellerId(member.getMemberId())
                        .stream()
                        .map(TransactionResponse::from)
                        .collect(Collectors.toList());
    }

    
}
