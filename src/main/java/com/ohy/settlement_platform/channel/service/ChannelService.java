package com.ohy.settlement_platform.channel.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.ohy.settlement_platform.channel.domain.Channel;
import com.ohy.settlement_platform.channel.dto.ChannelCreateRequest;
import com.ohy.settlement_platform.channel.dto.ChannelResponse;
import com.ohy.settlement_platform.channel.mapper.ChannelMapper;
import com.ohy.settlement_platform.common.exception.CustomException;
import com.ohy.settlement_platform.common.exception.ErrorCode;
import com.ohy.settlement_platform.member.domain.Member;
import com.ohy.settlement_platform.member.mapper.MemberMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChannelService {

    private final ChannelMapper channelMapper;
    private final MemberMapper memberMapper;

    /**
     * 채널 등록
     * @param email
     * @param requset
     */
    public void createChannel(String email, ChannelCreateRequest requset){
        //현재 로그인한 판매자 정보 조회
        Member member = memberMapper.findByEmail(email)
                .orElseThrow(()-> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

        Channel chanel = Channel.builder()
                .sellerId(member.getMemberId())
                .channelName(requset.getChannelName())
                .channelType(requset.getChannelType())
                .apiKey(UUID.randomUUID().toString())
                .status("ACTIVE")
                .build();

        channelMapper.insertChannel(chanel);
    }

    /***
     *  내 채널 목록 조회
     * @param email
     * @return
    */
    public List<ChannelResponse> getMyChannels(String email) {
        Member member = memberMapper.findByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

        return channelMapper.findBySellerId(member.getMemberId())
                .stream()
                .map(ChannelResponse::from) // Domain → Response 변환
                .collect(Collectors.toList());
    }

    public void updateChannelStatus(Long channelId, String status){
        channelMapper.findByChannelId(channelId)
                .orElseThrow(()-> new CustomException(ErrorCode.CHANNEL_NOT_FOUND));
                
        channelMapper.updateStatus(channelId, status);
    }
}
