package com.ohy.settlement_platform.channel.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ChannelCreateRequest {
    @NotBlank(message ="채널 이름을 입력해주세요.")
    private String channelName;
    
    @NotBlank(message ="채널 타입을 입력해주세요.")
    private String channelType;
}
