package com.ohy.settlement_platform.transaction.mapper;

import java.time.LocalDate;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ohy.settlement_platform.transaction.domain.Transaction;

@Mapper
public interface TransactionMapper {
    
    void insertTransaction(Transaction transaction);

    //중복 거래 확인
    boolean existsByChannelIdAndExternalTxId(
        @Param("channelId") Long channelId,
        @Param("externalTxId") String externalTxId
    );

    //판매자의모든거래 내역 조회
    List<Transaction> findBySellerId(Long sellerId);

    //특정 채널의 거래 내역조회
    List<Transaction> findByChannelId(Long channelId);

    //정산 배치에서 쓸 용되 특정 날짜의 특정 판매자 거래 조회
    List<Transaction> findBySellerIDAndDate(
        @Param("sellerId") Long sellerId,
        @Param("date") LocalDate date
    );

}
