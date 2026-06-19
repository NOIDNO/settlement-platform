package com.ohy.settlement_platform.channel.dto;

import java.time.LocalDateTime;

import com.ohy.settlement_platform.channel.domain.Channel;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ChannelResponse {
    private Long channelId;
    private String channelName;
    private String channelType;
    private String apiKey;       // 판매자가 거래 데이터 보낼 때 쓸 키
    private String status;
    private LocalDateTime createdAt;

    public static ChannelResponse from(Channel channel) {
    return new ChannelResponse(
            channel.getChannelId(),
            channel.getChannelName(),
            channel.getChannelType(),
            channel.getApiKey(),
            channel.getStatus(),
            channel.getCreatedAt()
    );
    }
}
