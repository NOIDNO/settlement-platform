package com.ohy.settlement_platform.channel.mapper;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ohy.settlement_platform.channel.domain.Channel;

@Mapper
public interface ChannelMapper {
    
    // 채널 등록
    void insertChannel(Channel channel);

    // 내 채널 목록 조회 (판매자 ID로 조회)
    List<Channel> findBySellerId(Long sellerId);

    // 채널 단건 조회
    Optional<Channel> findByChannelId(Long channelId);

    // api_key로 채널 조회 (거래 데이터 수신 시 인증용)
    Optional<Channel> findByApiKey(String apiKey);

    // 채널 상태 변경 (ACTIVE ↔ INACTIVE)
    void updateStatus(@Param("channelId") Long channelId, 
                      @Param("status") String status);

    // 채널 삭제
    void deleteChannel(Long channelId);


}
