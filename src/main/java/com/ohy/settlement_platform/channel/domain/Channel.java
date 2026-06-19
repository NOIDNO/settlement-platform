package com.ohy.settlement_platform.channel.domain;

import java.time.LocalDateTime;

import com.ohy.settlement_platform.channel.dto.ChannelResponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class     Channel {
    private Long channelId;
    private Long sellerId;       // 어떤 판매자의 채널인지
    private String channelName;  // 채널 이름 (ex. 내 쇼핑몰)
    private String channelType;  // 채널 종류 (SHOPPING, SUBSCRIPTION, OFFLINE)
    private String apiKey;       // 거래 데이터 수신 시 인증에 사용
    private String status;       // ACTIVE(활성) / INACTIVE(비활성)
    private LocalDateTime createdAt;
}
